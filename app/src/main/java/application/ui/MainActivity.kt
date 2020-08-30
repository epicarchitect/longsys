package application.ui

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import application.constants.VOID_ID
import application.controllers.courses.current_course.CurrentCourseController
import application.controllers.restarting.RestartController
import application.controllers.settings.SettingsController
import application.ui.pages.MainFragment
import application.ui.pages.consent.ConsentFragment
import kotlinx.android.synthetic.main.activity_main.*
import longsys.bb35.extreme.bulk.max.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this

        if (MainFragment.selectedId == -1) {
            if (CurrentCourseController(this).getCurrentCourseId() == VOID_ID) {
                MainFragment.selectedId = 0
            } else {
                MainFragment.selectedId = 1
            }
        }

        setTheme(SettingsController(this).getSettings().themeId())
        setContentView(R.layout.activity_main)
        checkConsent()

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        val navController = navHostFragment.findNavController()
        navHostFragment.retainInstance = true

        NavigationUI.setupActionBarWithNavController(this, navController, drawer)
        NavigationUI.setupWithNavController(navigationView, navController)

        RestartController(this).restart()
    }

    fun checkConsent() {
        val name = "Consent"
        val key = "isDone"
        val preferences = getSharedPreferences(name, Context.MODE_PRIVATE)
        val isDone = preferences.getBoolean(key, false)
        if (!isDone) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.mainContainer,
                    ConsentFragment().apply {
                        onDone {
                            preferences.edit().putBoolean(key, true).apply()
                            supportFragmentManager.beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                .remove(this)
                                .commit()
                        }
                    }
                )
                .commit()
        }
    }

    override fun onSupportNavigateUp() = NavigationUI.navigateUp(navHostFragment.findNavController(), drawer)

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        toolbar.title = getString(titleId)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        toolbar.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, navHostFragment.findNavController())
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var instance: MainActivity? = null
    }
}
