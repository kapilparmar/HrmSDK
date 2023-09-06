package com.connectandheal.hrmsdk.view.theme.hrm.routing

import android.net.Uri
import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass

@Parcelize
sealed class Destination: Parcelable {
    data class Fragment(@IdRes val id: Int): Destination()
    data class Activity(val clazz: Class<*>): Destination()
    data class NavDeepLink(val uri: Uri): Destination()
}

interface DestinationRouteProtocol: InputArgs {
    val destination: Destination
}

interface FragmentRouteProtocol: DestinationRouteProtocol {
    override val destination: Destination.Fragment
}

interface ActivityRouteProtocol: DestinationRouteProtocol {
    override val destination: Destination.Activity
    val startDestination: FragmentRouteProtocol?
}

interface ActionProtocol {
    val route: DestinationRouteProtocol?
}

val <T : Any> KClass<T>.companionClass: Class<*> get() {
    return Class.forName(this.java.name.removeSuffix("\$Companion"))
}