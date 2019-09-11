package com.github.bkhezry.fastadaptertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.bkhezry.fastadaptertest.model.ResponseEarthquake
import com.github.bkhezry.fastadaptertest.model.Feature
import com.github.bkhezry.fastadaptertest.util.ApiClient
import com.github.bkhezry.fastadaptertest.util.ApiService
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.ISelectionListener
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.select.SelectExtension
import com.mikepenz.fastadapter.select.getSelectExtension
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var responseEarthquake: ResponseEarthquake
    private val itemAdapter = ItemAdapter<Feature>()
    private lateinit var fastAdapter: FastAdapter<Feature>
    private lateinit var selectExtension: SelectExtension<Feature>
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiService = ApiClient.getClient()!!.create(ApiService::class.java)

        recyclerView = findViewById(R.id.recycler_view)
        fastAdapter = FastAdapter.with(itemAdapter)
        selectExtension = fastAdapter.getSelectExtension()
        selectExtension.apply {
            isSelectable = true
            multiSelect = false
            selectOnLongClick = false
            selectionListener = object : ISelectionListener<Feature> {
                override fun onSelectionChanged(item: Feature?, selected: Boolean) {
                    Toast.makeText(this@MainActivity, "SelectionChanged", Toast.LENGTH_LONG).show()
                    Log.i(
                        "FastAdapter",
                        "SelectedCount: " + selectExtension.selections.size + " ItemsCount: " + selectExtension.selectedItems.size
                    )
                }
            }
        }
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = fastAdapter

//        fastAdapter.onClickListener = { v: View?, _: IAdapter<Feature>, _: Feature, _: Int ->
//            if (v != null) {
//                Toast.makeText(
//                    v.context,
//                    "SelectedCount: " + selectExtension.selections.size + " ItemsCount: " + selectExtension.selectedItems.size,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            false
//        }
        getEarthquakeData()
    }

    private fun getEarthquakeData() {
        val subscribe = apiService.getHourlyEarthquake()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(responseData: ResponseEarthquake) {
        responseEarthquake = responseData
        itemAdapter.clear()
        itemAdapter.add(responseData.features)
//        for (item in responseData.features) {
//            val feature = Feature(item.typeString, item.properties, item.geometry, item.id)
//            itemAdapter.add(feature)
//        }
    }

    private fun handleError(error: Throwable) {
        Log.d("message:", error.localizedMessage)

    }
}
