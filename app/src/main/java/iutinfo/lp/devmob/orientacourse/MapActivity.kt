package iutinfo.lp.devmob.orientacourse

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationRequest
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapActivity : AppCompatActivity() {

    private var currentBalise = 0
    private lateinit var marker: Bitmap
    private lateinit var mapView: com.mapbox.maps.MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        activateLocation()

        mapView = findViewById(R.id.mapView)
        marker = bitmapFromDrawableRes(this, R.drawable.orangemarker)
        Bitmap.createScaledBitmap(marker, 10, 10, false)
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {

                    if(currentCourse.currentMode =="Grande Course") {
                        for (i in currentCourse.currentLongPoints){

                            addAnnotationToMap(i, currentCourse.currentLatPoints[currentCourse.currentLongPoints.indexOf(i)])
                        }
                    }
                    showNextMarkersBasedOnMode()


                }
            }
        )
        //Pour le mode édition
        //mapView.getMapboxMap().addOnMapClickListener() {

        //point -> addAnnotationToMap(point.longitude(), point.latitude())
        //    true

        //}

        //Permet de tester le bon fonctionnement de l'application sans avoir à réellement se déplacer aux balises.
        mapView.getMapboxMap().addOnMapClickListener() {
            point -> changeState(point.longitude(), point.latitude())
            true
        }
    }

    private fun showNextMarker(showNextThree: Boolean) {
        addAnnotationToMap(currentCourse.currentLongPoints[currentBalise], currentCourse.currentLatPoints[currentBalise])
        if(showNextThree) {
            addAnnotationToMap(currentCourse.currentLongPoints[currentBalise+1], currentCourse.currentLatPoints[currentBalise+1])
            addAnnotationToMap(currentCourse.currentLongPoints[currentBalise+2], currentCourse.currentLatPoints[currentBalise+2])
        }

    }

    private fun changeState(longitude: Double, latitude: Double) {
        if (currentCourse.currentMode == "Etoile" || currentCourse.currentMode == "Papillon") {
            if (Math.abs(currentCourse.currentLongPoints[currentBalise] - longitude) < 0.0004 && Math.abs(currentCourse.currentLatPoints[currentBalise] - latitude) < 0.0004) {
                marker = bitmapFromDrawableRes(this, R.drawable.greenmarker)
                addAnnotationToMap(currentCourse.currentLongPoints[currentBalise], currentCourse.currentLatPoints[currentBalise])
                currentBalise++
                if(currentBalise >= currentCourse.currentLongPoints.size){

                    startActivity(Intent(this, FinishedCourseActivity::class.java))
                    finish()
                } else {
                    showNextMarkersBasedOnMode()
                }


            }
        } else {
            for (i in currentCourse.currentLongPoints) {
                if (Math.abs(i - longitude) < 0.0004 && Math.abs(currentCourse.currentLatPoints[currentCourse.currentLongPoints.indexOf(i)] - latitude) < 0.0004) {
                    marker = bitmapFromDrawableRes(this, R.drawable.greenmarker)
                    addAnnotationToMap(i, currentCourse.currentLatPoints[currentCourse.currentLongPoints.indexOf(i)])
                    currentBalise++
                    if(currentBalise >= currentCourse.currentLongPoints.size){
                        startActivity(Intent(this, FinishedCourseActivity::class.java))
                        finish()
                    }
                    return
                }
            }
        }



    }



    private fun showNextMarkersBasedOnMode() {
        marker = bitmapFromDrawableRes(this, R.drawable.orangemarker)
        if(currentCourse.currentMode=="Etoile") {
            showNextMarker(false)
        } else if (currentCourse.currentMode == "Papillon") {
            if(currentBalise % 3 == 0) {
                showNextMarker(true)
            }
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int): Bitmap {
        val sourceDrawable = AppCompatResources.getDrawable(context, resourceId)!!
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            val constantState = sourceDrawable.constantState
            val drawable = constantState!!.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
    private fun addAnnotationToMap(long : Double, lat : Double) {
        val resizedMarker = Bitmap.createScaledBitmap(marker, 100, 100, false)
        val annotationApi = mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        pointAnnotationManager.deleteAll()
        val point = Point.fromLngLat(long, lat)
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(point)
            .withIconImage(resizedMarker)
            .withIconAnchor(IconAnchor.BOTTOM)
        pointAnnotationManager.create(pointAnnotationOptions)
    }


    //Fonction qui récupère la localisation de l'utilisateur. On rafraichit sa localisation toutes les 5 secondes
    fun activateLocation() {
        if((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) || (checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation.addOnSuccessListener {
                    location: Location? ->
                if(location != null) {
                user.currentLatitude = location.latitude;
                user.currentLongitude = location.longitude;
                Toast.makeText(this, "Dernière position connue: " + location?.latitude + ", " + location?.longitude, Toast.LENGTH_SHORT).show()
                }
            }
            val locationRequest = LocationRequest.create();
            locationRequest.interval = 5000;
            locationRequest.fastestInterval = 5000;
            locationRequest.smallestDisplacement = 20f;
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY;

            val locationCallBack = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult?: return
                    for(location in locationResult.locations) {
                        if (location != null) {
                            user.currentLatitude = location.latitude;
                            user.currentLongitude = location.longitude;
                            changeState(user.currentLongitude,user.currentLatitude)
                        }

                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.getMainLooper())
        }
    }
}