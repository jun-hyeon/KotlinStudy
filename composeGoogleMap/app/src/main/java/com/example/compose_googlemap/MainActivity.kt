package com.example.compose_googlemap

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.lang.IllegalStateException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var granted by remember { mutableStateOf(false) }

            val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {isGranted ->
                        granted = isGranted
                    }
            )
            if(ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                )
            {
                granted = true
            }
            if (granted){
                val viewModel = viewModel<MainViewModel>()
                lifecycle.addObserver(viewModel)
            MyMap(viewModel = viewModel)
            }else{
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "권한이 허용되지 않았습니다.")
                    Button(onClick = {
                        launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    }) {
                        Text(text = "권한 요청")
                    }
                }
            }
        }
    }
}

data class MapState(
    val location: Location?,
    val polylineOptions: PolylineOptions,
)
class MainViewModel(application: Application) : AndroidViewModel(application), LifecycleEventObserver{
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application.applicationContext)
    private val locationRequest : LocationRequest
    private val locationCallback : MyLocationCallBack


    private val _state = mutableStateOf(MapState(null, PolylineOptions().width(5f).color(Color.RED)))
    val state : State<MapState> = _state

    init {
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000).apply {
            setMinUpdateIntervalMillis(5000)
        }.build()
    }

    inner class MyLocationCallBack() : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult.lastLocation
            val polylineOptions = state.value.polylineOptions

            _state.value = state.value.copy(
                location = location,
                polylineOptions = polylineOptions.add(LatLng(location!!.latitude, location!!.longitude))
            )

        }
    }


    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun removeLocationListener(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if(event == Lifecycle.Event.ON_RESUME){
            addLocationListener()
        }else if(event == Lifecycle.Event.ON_PAUSE){
            removeLocationListener()
        }
    }
}

@Composable
fun MyMap(viewModel : MainViewModel){
    val map = rememberMapView()
    val state = viewModel.state.value
    AndroidView(factory = { map },
        update = {mapView ->
            mapView.getMapAsync { googleMap ->
                state.location?.let {
                    val latLng = LatLng(it.latitude, it.longitude)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                    googleMap.addPolyline(state.polylineOptions)
                    googleMap.addMarker(MarkerOptions().position(latLng).title("현재 위치"))
                }
            }
        }

    )
}

@Composable
fun rememberMapView() : MapView{
    val context = LocalContext.current
    val mapView = remember{MapView(context)}

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner){
        val observer = LifecycleEventObserver{_, event ->
            when(event){
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onStop()
                Lifecycle.Event.ON_STOP -> mapView.onPause()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }

        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    return mapView
}