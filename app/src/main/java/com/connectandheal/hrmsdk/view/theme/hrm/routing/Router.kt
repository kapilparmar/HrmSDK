package com.connectandheal.hrmsdk.view.theme.hrm.routing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import dagger.hilt.android.internal.managers.ViewComponentManager

fun Context.activity(): ComponentActivity? {
    return (
        (this as? ViewComponentManager.FragmentContextWrapper)?.baseContext
            ?: this
        ) as? ComponentActivity
}

data class Router(
    val context: Context? = null,
    val navController: NavController? = null
) {
    fun navigate(
        route: DestinationRouteProtocol,
        flags: Int? = null,
        bundle: Bundle? = null,
        launcher: ActivityResultLauncher<Intent>? = null
    ) {
        when (route) {
            is FragmentRouteProtocol -> {
                navController?.navigate(
                    resId = route.destination.id,
                    args = bundleOf(InputArgs.KEY to route)
                )
            }
            is ActivityRouteProtocol -> {
                val activity = context?.activity()
                if (activity?.javaClass != route.destination.clazz) {
                    val intent = Intent(context, route.destination.clazz)
                    intent.putExtra(InputArgs.KEY, route)
                    flags?.let { intent.addFlags(it) }
                    bundle?.let { intent.putExtras(it) }

                    if (launcher != null) {
                        launcher.launch(intent)
                    } else {
                        context?.startActivity(intent)
                    }
                } else {
                    route.startDestination?.let { route ->
                        navController?.navigate(
                            route.destination.id,
                            bundleOf(InputArgs.KEY to route)
                        )
                    }
                }
            }
        }
    }

    fun popToFragment(@IdRes destinationId: Int) {
        navController?.popBackStack(destinationId, false)
    }

    fun popToRoot() {
        navController?.popBackStack(navController.graph.startDestinationId, false)
    }

    fun popBackStack() {
        if (navController?.popBackStack() == false) {
            // If start destination, close activity
            closeActivity()
        }
    }

    fun closeActivity() {
        val activity = context?.activity()

        activity?.finish()
    }
}
