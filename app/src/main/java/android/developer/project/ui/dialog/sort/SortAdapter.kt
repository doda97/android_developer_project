package android.developer.project.ui.dialog.sort

import android.developer.project.base.BaseViewHolder
import android.developer.project.databinding.ItemSortBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SortAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {

    private val sortList = mutableListOf<String>()

    fun updateData(data: List<String>) {
        sortList.clear()
        sortList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemSortBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = sortList.size

    inner class RepositoryViewHolder(private val binding: ItemSortBinding) : BaseViewHolder(binding.root) {

        private lateinit var viewModel: SortItemViewModel

        override fun onBind(position: Int) {
            val sortItem = sortList[position]
            viewModel = SortItemViewModel(sortItem) {
                listener.invoke(it)
            }
            binding.viewModel = viewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            binding.executePendingBindings();
        }
    }
}