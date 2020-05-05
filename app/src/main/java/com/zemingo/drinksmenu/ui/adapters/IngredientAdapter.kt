package com.zemingo.drinksmenu.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zemingo.drinksmenu.R
import com.zemingo.drinksmenu.extensions.viewHolderInflate
import com.zemingo.drinksmenu.ui.models.IngredientUiModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_ingredient.view.*

class IngredientAdapter : DiffAdapter<IngredientUiModel, IngredientAdapter.IngredientViewHolder>() {

    var onClick: (() -> Unit)? = null

    inner class IngredientViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(ingredient: IngredientUiModel) {
            containerView.run {
                ingredient_name_tv.text = ingredient.name
                ingredient_quantity_tv.text = ingredient.quantity
                setOnClickListener { onClick?.invoke() }
            }
        }
    }

    override fun onBindViewHolder(
        holder: IngredientViewHolder,
        data: IngredientUiModel,
        position: Int
    ) {
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            parent.viewHolderInflate(R.layout.list_item_ingredient)
        )
    }
}