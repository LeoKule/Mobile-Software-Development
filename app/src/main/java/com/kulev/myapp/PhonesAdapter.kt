package com.kulev.myapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PhonesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Список для хранения данных внутри адаптера
    private var PhonesList: ArrayList<PhoneModel> = ArrayList()

    // Метод для загрузки данных в адаптер
    fun setupPhones(phonesList: Array<PhoneModel>) {
        PhonesList.clear()
        PhonesList.addAll(phonesList)
        notifyDataSetChanged()
    }

    // Создание ViewHolder (ссылка на макет элемента)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return PhonesViewHolder(itemView)
    }

    // Привязка данных к ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhonesViewHolder) {
            holder.bind(PhonesList[position])
        }
    }

    // Возвращает количество элементов
    override fun getItemCount(): Int {
        return PhonesList.count()
    }

    // Класс ViewHolder для кэширования ссылок на View
    class PhonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Находим View элементы через findViewById
        private val tvName: TextView = itemView.findViewById(R.id.tv_phone_name)
        private val tvPrice: TextView = itemView.findViewById(R.id.tv_price)
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val tvScore: TextView = itemView.findViewById(R.id.tv_score)

        fun bind(mPhones: PhoneModel) {
            tvName.text = mPhones.name
            tvPrice.text = mPhones.price
            tvDate.text = mPhones.date
            tvScore.text = mPhones.score
        }
    }
}