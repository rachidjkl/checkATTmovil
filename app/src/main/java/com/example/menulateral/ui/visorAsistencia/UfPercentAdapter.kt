    package com.example.menulateral.ui.visorAsistencia

    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.TextView
    import androidx.recyclerview.widget.RecyclerView
    import com.example.menulateral.DataModel.FaltaToShow
    import com.example.menulateral.DataModel.ModuloUFVisorAsistencia
    import com.example.menulateral.R
    import com.example.menulateral.DataModel.Uf

    class UfPercentAdapter( private val ufList: List<ModuloUFVisorAsistencia>):
        RecyclerView.Adapter <UfPercentAdapter.UfViewHolder> (){

        companion object{
            var selectedUF = mutableListOf<ModuloUFVisorAsistencia>()
        }

        private val layout = R.layout.item_uf_percent
        private var clickListener: View.OnClickListener? = null

        class UfViewHolder (val view: View): RecyclerView.ViewHolder(view){
            var hourAbsence: TextView
            var moduleNameAbsence: TextView
            var ufNameColorRectangle: TextView


            init {
                hourAbsence = view.findViewById(R.id.hourAbsenceColorRectangle)
                moduleNameAbsence = view.findViewById(R.id.moduleColorRectangle)
                ufNameColorRectangle = view.findViewById(R.id.ufNameColorRectangle)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UfViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
            return UfViewHolder(view)
        }
        override fun onBindViewHolder(holder: UfViewHolder, position: Int) {
            val uf = faltas [position]
            bindPackage(holder, uf)
        }

        override fun getItemCount(): Int {
            return faltas.size
        }

        fun bindPackage(holder: UfViewHolder, uf: ModuloUFVisorAsistencia){
            holder.ufName?.text = uf.nombre_uf
            holder.ufPercentage?.text = (uf.porcentaje_asistencia.toString()) + "%"

        }

        private fun sumaPorcentajes(){

        }



    }