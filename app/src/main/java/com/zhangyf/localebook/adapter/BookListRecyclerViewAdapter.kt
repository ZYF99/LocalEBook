package com.zhangyf.localebook.adapter


import com.zhangyf.localebook.R
import com.zhangyf.localebook.databinding.ItemBookBinding

class BookListRecyclerViewAdapter(
    onClickAction: (String) -> Unit
) : BaseRecyclerAdapter<String, ItemBookBinding>(
    R.layout.item_book,
    onClickAction
) {
    override fun bindData(binding: ItemBookBinding, position: Int) {
        binding.bookName = baseList[position]
    }
}