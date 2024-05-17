package com.example.dogbreedjava.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.dogbreedjava.R
import com.example.dogbreedjava.adapter.DogGallaryAdapter
import com.example.dogbreedjava.presenter.FetchDogBreedPresenter
import com.example.dogbreedjava.presenter.FetchImageUrlPresenter
import com.example.dogbreedjava.presenter.IFetchDogBreedsPresenter
import com.example.dogbreedjava.presenter.IFetchImageUrlPresenter
import com.example.dogbreedjava.utils.SpaceItemDecoration
import com.example.dogbreedjava.view.IDogTypeView
import com.example.dogbreedjava.view.IImageFetchView
import java.io.Serializable

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/14
 */
class DogAlbumActivity : BaseActivity(), IImageFetchView, IDogTypeView {
    private var recyclerView: RecyclerView? = null
    private var dogTypeName = "dachshund"
    private var textView: TextView? = null
    private var dogGallaryAdapter: DogGallaryAdapter? = null
    private val columnNumber = 3
    private var dogImageUrls: Array<String?>? = null
    private var dogTypeNames: Array<String?>? = null
    private var dogTypeNameMap: Map<String, List<String>>? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var imageViewResearch: ImageView? = null
    private var fetchImageUrlPresenter: IFetchImageUrlPresenter? = null
    private var fetchDogBreedsPresenter: IFetchDogBreedsPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.gallary_dog_breed_layout)
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
        val intent = intent
        dogImageUrls = intent.getStringArrayExtra("dogImageUrls")
    }

    override fun setupView() {
        imageViewResearch = findViewById(R.id.dog_search_imageview)
        imageViewResearch?.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this, DogSearchActivity::class.java)
            intent.putExtra("dogTypeNameMap", dogTypeNameMap as Serializable?)
            intent.putExtra("dogTypeNames", dogTypeNames)
            startActivityForResult(intent, 1)
        })
        textView = findViewById(R.id.dog_name)
        swipeRefreshLayout = findViewById(R.id.dog_swipeRefresh)
        swipeRefreshLayout?.setOnRefreshListener(OnRefreshListener {
            val toast =
                Toast.makeText(this, "Welcome To DogCeo", Toast.LENGTH_SHORT)
            toast.show()
            swipeRefreshLayout?.setRefreshing(false)
        })
        textView?.setText("Breed: $dogTypeName")
        recyclerView = findViewById(R.id.show_dog_gallary)
        recyclerView?.setLayoutManager(GridLayoutManager(this, columnNumber))
        val space = 5
        recyclerView?.addItemDecoration(SpaceItemDecoration(space))
        dogGallaryAdapter = DogGallaryAdapter(this)
        dogGallaryAdapter!!.setOnItemClickListener { dogTypeName: String?, currentIndex: Int ->
            val intent = Intent(this, DogGallaryActivity::class.java)
            intent.putExtra("currentIndex", currentIndex)
            intent.putExtra("dogTypeName", dogImageUrls)
            startActivity(intent)
        }
        recyclerView?.setAdapter(dogGallaryAdapter)
    }

    override fun initPresenter() {
        fetchImageUrlPresenter = FetchImageUrlPresenter(this)
        fetchDogBreedsPresenter = FetchDogBreedPresenter(this)
    }

    override fun networkRequest() {
        if (dogImageUrls != null && dogImageUrls!!.size > 0) {
            val dogImageUrlList: MutableList<String?> = ArrayList()
            for (i in dogImageUrls!!.indices) {
                dogImageUrlList.add(dogImageUrls!![i])
                dogGallaryAdapter!!.updateDataChanged(dogImageUrlList)
            }
        } else {
            fetchImageUrlPresenter!!.fetchImageUrlFromNet(dogTypeName)
        }
        fetchDogBreedsPresenter!!.fetchDogBreeds()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null || resultCode != RESULT_OK) {
            return
        }
        val dogTypeNameStr = data.getStringExtra("dogTypeName")
        if (dogTypeNameStr != null) {
            dogTypeName = dogTypeNameStr
            swipeRefreshLayout!!.isRefreshing = true
        }
        fetchImageUrlPresenter!!.fetchImageUrlFromNet(dogTypeName)
        textView!!.text = "Breed: $dogTypeName"
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun fetchImageUrlsFromNet(imageUrls: Array<String?>) {
        val dogImageUrlList: MutableList<String?> = ArrayList()
        for (i in imageUrls.indices) {
            dogImageUrlList.add(imageUrls[i])
        }
        dogImageUrls = imageUrls
        dogGallaryAdapter!!.updateDataChanged(dogImageUrlList)
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun fetchDogBreeds(
        dogTypeNameMap: Map<String, List<String>>?,
        dogTypeNames: Array<String?>?
    ) {
        this.dogTypeNameMap = dogTypeNameMap
        this.dogTypeNames = dogTypeNames
    }

    override fun fetchDogBreedsFailed() {}
    override fun fetchImageUrlsFailed() {}

}
