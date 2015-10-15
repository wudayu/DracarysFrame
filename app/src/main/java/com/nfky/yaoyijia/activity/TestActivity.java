package com.nfky.yaoyijia.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.overlay.BusRouteOverlay;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.maps2d.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.generic.Utils;

/**
 * Created by David on 5/27/15.
 * <p/>
 * 用来测试内置地图功能
 */
public class TestActivity extends BaseActivity implements LocationSource, AMapLocationListener, SensorEventListener, RouteSearch.OnRouteSearchListener {

	private MapView mapView;
	private AMap aMap;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastTime = 0;
    private final int TIME_SENSOR = 100;
    private float mAngle;

    private Marker mGPSMarker;

    private LatLng dstPoint;
    private LatLng staPoint;

    private Button busButton;
    private Button drivingButton;
    private Button walkButton;

    private BusRouteResult busRouteResult;// 公交模式查询结果
    private DriveRouteResult driveRouteResult;// 驾车模式查询结果
    private WalkRouteResult walkRouteResult;// 步行模式查询结果

	@Override
	protected void initContainer() {
		setContentView(R.layout.activity_test);
	}

	@Override
	protected void initComponents() {
		mapView = (MapView) findViewById(R.id.amv_main);
		mapView.onCreate(savedInstanceState);
        busButton = (Button) findViewById(R.id.btn_transit);
        drivingButton = (Button) findViewById(R.id.btn_driving);
        walkButton = (Button) findViewById(R.id.btn_walk);

        //初始化传感器
        mSensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	@Override
	protected void initEvents() {
        busButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busRoute();
            }
        });
        drivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drivingRoute();
            }
        });
        walkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walkRoute();
            }
        });
	}

	@Override
	protected void initData() {
        // 暂定一个位置（南京新街口）
        dstPoint = new LatLng(32.047449d, 118.790911d);
	}

	@Override
	protected void afterAllSet() {
        init();
	}

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        mGPSMarker = aMap.addMarker(
                new MarkerOptions().icon(
                        BitmapDescriptorFactory
                                .fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker))
                ).anchor((float)0.5, (float)0.5));

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        registerSensorListener();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 此方法已经废弃
     */
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /**
     * The Count.
     */
    int count = 0;
    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null && aLocation != null) {
            //mListener.onLocationChanged(aLocation);// 显示系统小蓝点
            staPoint = new LatLng(aLocation.getLatitude(), aLocation.getLongitude());
            mGPSMarker.setPosition(staPoint);
            LatLng midPoint = new LatLng((staPoint.latitude + dstPoint.latitude) / 2, (staPoint.longitude + dstPoint.longitude) / 2);
            if (count < 1) {
                aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(midPoint, 16, 0, 0)), 2000, null);
                searchRouteResult(new LatLonPoint(staPoint.latitude, staPoint.longitude), new LatLonPoint(dstPoint.latitude, dstPoint.longitude));
                count++;
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 2000, 10, this);
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
        unRegisterSensorListener();
    }

    /**
     * Register sensor listener.
     */
    public void registerSensorListener() {
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Un register sensor listener.
     */
    public void unRegisterSensorListener() {
        mSensorManager.unregisterListener(this, mSensor);
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }
    //回调函数处理逻辑
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (System.currentTimeMillis() - lastTime < TIME_SENSOR) {
            return;
        }
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION: {
                float x = event.values[0];

                x += getScreenRotationOnPhone(this);
                x %= 360.0F;
                if (x > 180.0F)
                    x -= 360.0F;
                else if (x < -180.0F)
                    x += 360.0F;
                if (Math.abs(mAngle -90+ x) < 3.0f) {
                    break;
                }
                mAngle = x;
                if (mGPSMarker != null) {
                    mGPSMarker.setRotateAngle(-mAngle);
                    aMap.invalidate();
                }
                lastTime = System.currentTimeMillis();
            }
        }

    }

    /**
     * 获取当前屏幕旋转角度
     *
     * @param context activity
     * @return 0表示是竖屏 ; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public static int getScreenRotationOnPhone(Context context) {
        final Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }

    private int routeType = 1;
    private int busMode = RouteSearch.BusDefault;// 公交默认模式
    private int drivingMode = RouteSearch.DrivingDefault;// 驾车默认模式
    private int walkMode = RouteSearch.WalkDefault;// 步行默认模式
    private RouteSearch routeSearch = null;

    /**
     * 开始搜索路径规划方案
     *
     * @param startPoint the start point
     * @param endPoint   the end point
     */
    public void searchRouteResult(LatLonPoint startPoint, LatLonPoint endPoint) {
        showProcessingDialog(null, true, null);
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                startPoint, endPoint);
        if (routeType == 1) {// 公交路径规划
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, busMode, "南京", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            routeSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        } else if (routeType == 2) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, drivingMode,
                    null, null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            routeSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == 3) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, walkMode);
            routeSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    /**
     * 选择公交模式
     */
    private void busRoute() {
        routeType = 1;// 标识为公交模式
        busMode = RouteSearch.BusDefault;
        drivingButton.setBackgroundResource(R.drawable.mode_driving_off);
        busButton.setBackgroundResource(R.drawable.mode_transit_on);
        walkButton.setBackgroundResource(R.drawable.mode_walk_off);
        searchRouteResult(new LatLonPoint(staPoint.latitude, staPoint.longitude), new LatLonPoint(dstPoint.latitude, dstPoint.longitude));
    }

    /**
     * 选择驾车模式
     */
    private void drivingRoute() {
        routeType = 2;// 标识为驾车模式
        drivingButton.setBackgroundResource(R.drawable.mode_driving_on);
        busButton.setBackgroundResource(R.drawable.mode_transit_off);
        walkButton.setBackgroundResource(R.drawable.mode_walk_off);
        searchRouteResult(new LatLonPoint(staPoint.latitude, staPoint.longitude), new LatLonPoint(dstPoint.latitude, dstPoint.longitude));
    }

    /**
     * 选择步行模式
     */
    private void walkRoute() {
        routeType = 3;// 标识为步行模式
        walkMode = RouteSearch.WalkMultipath;
        drivingButton.setBackgroundResource(R.drawable.mode_driving_off);
        busButton.setBackgroundResource(R.drawable.mode_transit_off);
        walkButton.setBackgroundResource(R.drawable.mode_walk_on);
        searchRouteResult(new LatLonPoint(staPoint.latitude, staPoint.longitude), new LatLonPoint(dstPoint.latitude, dstPoint.longitude));
    }

    /**
     * 公交路线查询回调
     */
    @Override
    public void onBusRouteSearched(BusRouteResult result, int rCode) {
        dismissProcessingDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                busRouteResult = result;
                BusPath busPath = busRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap,
                        busPath,

                        busRouteResult.getStartPos(),
                        busRouteResult.getTargetPos()


                );
                routeOverlay.removeFromMap();
                routeOverlay.addToMap();
                routeOverlay.zoomToSpan();
            } else {
                Utils.debug("no result");
            }
        } else if (rCode == 27) {
            Utils.debug("error_network");
        } else if (rCode == 32) {
            Utils.debug("error_key");
        } else {
            Utils.debug("error_other" + rCode);
        }
    }

    /**
     * 驾车结果回调
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int rCode) {
        dismissProcessingDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                driveRouteResult = result;
                DrivePath drivePath = driveRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                        this, aMap, drivePath, driveRouteResult.getStartPos(),
                        driveRouteResult.getTargetPos());
                drivingRouteOverlay.removeFromMap();
                drivingRouteOverlay.addToMap();
                drivingRouteOverlay.zoomToSpan();
            } else {
                Utils.debug("no result");
            }
        } else if (rCode == 27) {
            Utils.debug("error_network");
        } else if (rCode == 32) {
            Utils.debug("error_key");
        } else {
            Utils.debug("error_other" + rCode);
        }
    }

    /**
     * 步行路线结果回调
     */
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int rCode) {
        dismissProcessingDialog();
        if (rCode == 0) {
            if (result != null && result.getPaths() != null
                    && result.getPaths().size() > 0) {
                walkRouteResult = result;
                WalkPath walkPath = walkRouteResult.getPaths().get(0);
                aMap.clear();// 清理地图上的所有覆盖物
                WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(this,
                        aMap, walkPath, walkRouteResult.getStartPos(),
                        walkRouteResult.getTargetPos());
                walkRouteOverlay.removeFromMap();
                walkRouteOverlay.addToMap();
                walkRouteOverlay.zoomToSpan();
            } else {
                Utils.debug("no result");
            }
        } else if (rCode == 27) {
            Utils.debug("error_network");
        } else if (rCode == 32) {
            Utils.debug("error_key");
        } else {
            Utils.debug("error_other" + rCode);
        }
    }

}
