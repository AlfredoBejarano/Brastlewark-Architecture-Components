package me.alfredobejarano.brastlewarkarchitecturecomponents.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.alfredobejarano.brastlewarkarchitecturecomponents.databinding.ItemGnomeBinding
import me.alfredobejarano.brastlewarkarchitecturecomponents.model.Gnome
import me.alfredobejarano.brastlewarkarchitecturecomponents.utils.asCleanList

class GnomesAdapter(
    private var gnomes: List<Gnome>,
    private val onItemSelected: (gnome: Gnome) -> Unit
) :
    RecyclerView.Adapter<GnomesAdapter.GnomeItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemGnomeBinding.inflate(LayoutInflater.from(parent.context), parent, false).run {
            GnomeItem(this, onItemSelected)
        }

    override fun getItemCount() = gnomes.size
    override fun onBindViewHolder(holder: GnomeItem, position: Int) = holder.bind(gnomes[position])

    fun setGnomes(newGnomes: List<Gnome>) {
        val callback = GnomeDiffCallback(gnomes, newGnomes)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        gnomes = newGnomes
    }

    class GnomeDiffCallback(
        private val oldGnomes: List<Gnome>,
        private val newGnomes: List<Gnome>
    ) : DiffUtil.Callback() {
        override fun getNewListSize() = newGnomes.size
        override fun getOldListSize() = oldGnomes.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldGnomes[oldItemPosition].id == newGnomes[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            areItemsTheSame(oldItemPosition, newItemPosition)
    }

    class GnomeItem(
        private val binding: ItemGnomeBinding,
        private val onItemSelected: (gnome: Gnome) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gnome: Gnome) {
            binding.gnome = gnome
            binding.gnomeProfessions.text = gnome.professions.asCleanList()
            binding.root.setOnClickListener {
                onItemSelected(gnome)
            }

        }
    }
}