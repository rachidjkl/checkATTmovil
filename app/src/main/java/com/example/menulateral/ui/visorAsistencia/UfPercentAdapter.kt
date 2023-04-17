    package com.example.menulateral.ui.visorAsistencia

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.menulateral.R
    import com.example.menulateral.DataModel.Uf

    class UfPercentAdapter( private val ufList: MutableList<Uf>):
        RecyclerView.Adapter <UfPercentAdapter.UfViewHolder> (){

        private val layout = R.layout.item_uf_percent
        private var clickListener: View.OnClickListener? = null

        class UfViewHolder (val view: View): RecyclerView.ViewHolder(view){
            var ufName: TextView
            var ufPercentage: TextView


            init {
                ufName = view.findViewById(R.id.ufName)
                ufPercentage = view.findViewById(R.id.ufPercentage)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return UfViewHolder(view)
        }
        override fun onBindViewHolder(holder: UfViewHolder, position: Int) {
            val uf = ufList [position]
            bindPackage(holder, uf)
        }

        override fun getItemCount(): Int {
            return ufList.size
        }

        fun bindPackage(holder: UfViewHolder, uf: Uf){


            holder.ufName?.text = uf.nombre_completo
            holder.ufPercentage?.text = (uf.horas_cursadas).toInt().toString()+"%"

        }



    }