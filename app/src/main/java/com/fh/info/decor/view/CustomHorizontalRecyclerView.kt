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

class CustomHorizontalRecyclerView(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {
    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

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
                        view.forEachVisibleHolder { horizontalViewHolder: HorizontalListViewViewHolder ->
                            horizontalViewHolder.rotation.start()
                            horizontalViewHolder.translationX.start()
                        }
                    }

                    override fun onAbsorb(velocity: Int) {
                        super.onAbsorb(velocity)
                        val sign = if (direction == DIRECTION_RIGHT) -1 else 1
                        val translationVelocity = sign * velocity * flingTranslationMagnitude
                        view.forEachVisibleHolder { horizontalViewHolder: HorizontalListViewViewHolder ->
                            horizontalViewHolder.translationX
                                .setStartVelocity(translationVelocity)
                                .start()

                        }
                    }

                    fun handlePull(deltaDistance: Float) {
                        println(direction)
                        val sign = if (direction == DIRECTION_RIGHT) 1 else -1
                        val rotationDelta = sign * deltaDistance * overScrollRotationMagnitude
                        val translationXDelta = sign * view.height * deltaDistance * overScrollTranslationMagnitude

                        view.forEachVisibleHolder { holder: HorizontalListViewViewHolder ->
                            holder.rotation.cancel()
                            holder.translationX.cancel()
                            holder.itemView.rotation += rotationDelta
                            holder.itemView.translationX -= translationXDelta
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

    private inline fun <reified T : HorizontalListViewViewHolder> RecyclerView.forEachVisibleHolder(
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

open class HorizontalListViewViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    var currentVelocity = 0f

    val rotation: SpringAnimation = SpringAnimation(binding.root, SpringAnimation.ROTATION)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )
        .addUpdateListener { _, _, velocity ->
            currentVelocity = velocity
        }

    val translationX: SpringAnimation = SpringAnimation(binding.root, SpringAnimation.TRANSLATION_X)
        .setSpring(
            SpringForce()
                .setFinalPosition(0f)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
        )


}