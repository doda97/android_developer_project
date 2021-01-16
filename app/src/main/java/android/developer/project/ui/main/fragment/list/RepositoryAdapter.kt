package android.developer.project.ui.main.fragment.list

import android.developer.project.base.BaseViewHolder
import android.developer.project.data.model.ui.Repository
import android.developer.project.databinding.ItemRepositoryBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RepositoryAdapter(private val listener: (Repository) -> Unit, private val avatarListener: (Repository) -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {

    private val repositories = mutableListOf<Repository>()

    fun updateData(data: List<Repository>) {
        repositories.clear()
        repositories.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = repositories.size

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) : BaseViewHolder(binding.root) {

        private lateinit var viewModel: RepositoryItemViewModel

        override fun onBind(position: Int) {
            val repository = repositories[position]
            viewModel = RepositoryItemViewModel(repository, {
                listener.invoke(it)
            }, {
                avatarListener.invoke(it)
            } )
            viewModel.watchers.value = repository.watchers.toString()
            viewModel.forks.value = repository.forks.toString()
            viewModel.issues.value = repository.issues.toString()
            binding.viewModel = viewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            binding.executePendingBindings();
        }
    }
}