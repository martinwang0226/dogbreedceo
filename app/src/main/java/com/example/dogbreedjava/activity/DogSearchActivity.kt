package com.example.dogbreedjava.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.dogbreedjava.R
import com.example.dogbreedjava.adapter.DogTypeAdapter
import com.example.dogbreedjava.listener.DogItemClickListener
import com.example.dogbreedjava.presenter.FetchDogBreedPresenter
import com.example.dogbreedjava.presenter.IFetchDogBreedsPresenter
import com.example.dogbreedjava.utils.SpaceItemDecoration
import com.example.dogbreedjava.utils.StringUtils.dogTypeFilter
import com.example.dogbreedjava.view.IDogTypeView

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/14
 */
class DogSearchActivity : BaseActivity(), SearchView.OnQueryTextListener, IDogTypeView {
    private var mRecyclerView: RecyclerView? = null
    private var dogTypeNameList: List<String>? = null
    private var dogTypeAdapter: DogTypeAdapter? = null
    private var searchView: SearchView? = null
    private var dogTypeNames: Array<String?>? = null
    private var dogTypeNameMap: Map<String, List<String>>? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var backView: ImageView? = null
    private var fetchDogBreedsPresenter: IFetchDogBreedsPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.main_dog_breed_layout)
        super.onCreate(savedInstanceState)
    }

    override fun setupView() {
        searchView = findViewById(R.id.dog_type_search)
        swipeRefreshLayout = findViewById(R.id.dog_swipeRefresh)
        backView = findViewById(R.id.back_icon)
        backView?.setOnClickListener { v: View? -> finish() }
        searchView?.setOnQueryTextListener(this)
        mRecyclerView = findViewById(R.id.show_dog_type)
        mRecyclerView?.setLayoutManager(GridLayoutManager(this, 1))
        val space = 1
        mRecyclerView?.addItemDecoration(SpaceItemDecoration(space))
        dogTypeAdapter = DogTypeAdapter(this)
        dogTypeAdapter!!.setOnItemClickListener(object : DogItemClickListener {
            override fun OnItemClickListener(dogTypeName: String?, index: Int) {
                val intent = Intent()
                intent.putExtra("dogTypeName", dogTypeName)
                setResult(RESULT_OK, intent)
                finish()
            }
        })
        dogTypeAdapter!!.setOnItemClickListener(object : DogItemClickListener {
            override fun OnItemClickListener(dogTypeName: String?, index: Int) {
                val intent = Intent()
                intent.putExtra("dogTypeName", dogTypeName)
                setResult(RESULT_OK, intent)
                finish()
            }
        })
        mRecyclerView?.setAdapter(dogTypeAdapter)
    }

    override fun initPresenter() {
        fetchDogBreedsPresenter = FetchDogBreedPresenter(this)
    }

    override fun initData() {
        dogTypeNameList = ArrayList()
        val intent = intent
        dogTypeNames = intent.getStringArrayExtra("dogTypeNames")
        dogTypeNameMap =
            getIntent().getSerializableExtra("dogTypeNameMap") as Map<String, List<String>>?
    }

    override fun networkRequest() {
        if (dogTypeNameMap != null && dogTypeNameMap!!.size > 0) {
            dogTypeAdapter!!.updateDogNameChanged(dogTypeNames, dogTypeNameMap)
        } else {
            swipeRefreshLayout!!.isRefreshing = true
            fetchDogBreedsPresenter!!.fetchDogBreeds()
        }
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (query != null) {
            val dogSubTypeName = dogTypeFilter(dogTypeNames, query)
            dogTypeAdapter!!.updateDogNamesChanged(dogSubTypeName)
        } else {
            dogTypeNames?.let { dogTypeAdapter!!.updateDogNamesChanged(it) }
        }
        runOnUiThread { backView!!.visibility = View.VISIBLE }
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun fetchDogBreeds(
        dogTypeNameMap: Map<String, List<String>>?,
        dogTypeNames: Array<String?>?
    ) {
        this.dogTypeNameMap = dogTypeNameMap
        this.dogTypeNames = dogTypeNames
        dogTypeAdapter!!.updateDogNameChanged(dogTypeNames, dogTypeNameMap)
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun fetchDogBreedsFailed() {}
}
