package com.example.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.assignment.data.AuthViewModel
import com.example.assignment.data.CustomerViewModel
import com.example.assignment.data.RatingViewModel
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.databinding.HeaderBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var abc: AppBarConfiguration
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private val customerViewModel: CustomerViewModel by viewModels()
    private val ratingViewModel: RatingViewModel by viewModels()
    private val auth: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //to make every top page have "="
        abc = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.loginFragment, R.id.productFragment, R.id.paymentFragment, R.id.deliveryFragment, R.id.coinFragment, R.id.setting),
            binding.drawerLayout
        )

        //initialize the view model data
        customerViewModel.init()
        ratingViewModel.init()


        //RESTORE_DATA()
        //show '='
        setupActionBarWithNavController(nav, abc)

        binding.navView.setupWithNavController(nav)
        auth.getCustomerLiveData().observe(this) { customer ->

            //Clear menu = remove header
            binding.navView.menu.clear()
            val h = binding.navView.getHeaderView(0)
            binding.navView.removeHeaderView(h)

            //Inflate menu
            if (customer == null){ //logout
                binding.navView.inflateMenu(R.menu.drawer)
                binding.navView.inflateHeaderView(R.layout.header2)
                nav.popBackStack(R.id.homeFragment, false)
                nav.navigateUp()
            }
            else { //login
                binding.navView.inflateMenu(R.menu.drawer_login)
                binding.navView.inflateHeaderView(R.layout.header)
                setHeader(customer)
            }

            binding.navView.menu.findItem(R.id.logout)?.setOnMenuItemClickListener { logout() }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }

    //display ':' and menu item
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    //let menu item fucntion
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(nav) || super.onOptionsItemSelected(item)
    }

    //header
    private fun setHeader(user: Customer) {
        val h = binding.navView.getHeaderView(0)
        val b = HeaderBinding.bind(h)

     //   b.imgPhoto.setImageBlob(user.photo)
        b.txtUserName.text  = user.name
        b.txtUserEmail.text = user.email
    }

    //log out
    private fun logout(): Boolean {
        // TODO(4): Logout -> auth.logout(...)
        //          Clear navigation backstack
        auth.logout(this)
        nav.popBackStack(R.id.homeFragment, false)
        nav.navigateUp()

        binding.drawerLayout.close()
        return true
    }
}