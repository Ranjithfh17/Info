package com.fh.info.decor.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class CustomVerticalRecyclerView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {
    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        this.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return object : EdgeEffect(view.context) {
                    override fun onPull(deltaDistance: Float) {
                        super.onPull(deltaDistance)
                        handlePull(deltaDistance)

                    }

                    override fun onPull(deltaDistance: Float, displacement: Float) {
                        super.onPull(deltaDistance, displacement)
                        handlePull(deltaDistance)
                    }

                    override fun onRelease() {
                        super.onRelease()
                        view.forEachVisibleHolder { horizontalViewHolder: VerticalListViewHolder ->
                            horizontalViewHolder.rotation.start()
                            horizontalViewHolder.translationY.start()
                        }
                    }

                    override fun onAbsorb(velocity: Int) {
                        super.onAbsorb(velocity)
                        val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                        val translationVelocity = sign * velocity * flingTranslationMagnitude
                        view.forEachVisibleHolder { horizontalViewHolder: VerticalListViewHolder ->
                            horizontalViewHolder.translationY
                                .setStartVelocity(translationVelocity)
                                .start()

                        }
                    }

                    fun handlePull(deltaDistance: Float) {
                        val sign = if (direction == DIRECTION_BOTTOM) -1 else 1
                        val rotationDelta = sign * deltaDistance * overScrollRotationMagnitude
                        val translationYDelta =
                            sign * view.width * deltaDistance * overScrollTranslationMagnitude

                        view.forEachVisibleHolder { horizontalViewHolder: VerticalListViewHolder ->
                            horizontalViewHolder.rotation.cancel()
                            horizontalViewHolder.translationY.cancel()
                            horizontalViewHolder.itemView.rotation += rotationDelta
                            horizontalViewHolder.itemView.translationY += translationYDelta

                        }

                    }
                }


            }

        }

    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.stateRestorationPolicy = Adapter.StateRestorationPolicy.ALLOW
        scheduleLayoutAnimation()
    }

    private inline fun <reified T : VerticalListViewHolder> RecyclerView.forEachVisibleHolder(
        action: (T) -> Unit
    ) {
        for (i in 0 until childCount) {
            action(getChildViewHolder(getChildAt(i)) as T)
        }

    }

    companion object {
        private const val value = 1.0f
        const val flingTranslationMagnitude = value
        const val overScrollRotationMagnitude = value
        const val overScrollTranslationMagnitude = value

    }


}

open class VerticalListViewHolder(itemView: ViewBinding) : RecyclerView.ViewHolder(itemView.root) {

    var currentVelocity = 0f

    val rotation: SpringAnimation = SpringAnimation(itemView.root, SpringAnimation.ROTATION)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )
        .addUpdateListener { _, _, velocity ->
            currentVelocity = velocity
        }

    val translationY: SpringAnimation = SpringAnimation(itemView.root, SpringAnimation.TRANSLATION_Y)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )


}