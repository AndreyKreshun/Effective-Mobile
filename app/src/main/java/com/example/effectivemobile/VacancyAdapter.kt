import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivemobile.R
import com.example.effectivemobile.data.Vacancy

class VacancyAdapter(private val vacancies: MutableList<Vacancy>) :
    RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_vacancy, parent, false)
        return VacancyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
    }

    override fun getItemCount() = vacancies.size

    // Добавляем метод для обновления данных
    fun updateData(newVacancies: MutableList<Vacancy>) {
        vacancies.clear()
        vacancies.addAll(newVacancies)
        notifyDataSetChanged()  // Сообщаем RecyclerView, что данные изменились
    }

    inner class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(vacancy: Vacancy) {
            // Привязка данных вакансии к элементам
        }
    }
}
