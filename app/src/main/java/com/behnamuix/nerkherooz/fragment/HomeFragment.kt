package com.behnamuix.nerkherooz.fragment

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.behnamuix.nerkherooz.R
import com.behnamuix.nerkherooz.adapter.AdsAdpater
import com.behnamuix.nerkherooz.adapter.RecyclerMainAdapter
import com.behnamuix.nerkherooz.databinding.FragmentHomeBinding
import com.behnamuix.nerkherooz.model.Ads
import com.behnamuix.nerkherooz.model.Profile
import com.behnamuix.nerkherooz.remote.gold.GoldApiRepository
import com.behnamuix.nerkherooz.remote.gold.GoldReq
import com.behnamuix.nerkherooz.remote.goldModel.ContentModel
import com.behnamuix.nerkherooz.remote.goldModel.GoldModel
import com.behnamuix.nerkherooz.remote.time.TimeApiRepository
import com.behnamuix.nerkherooz.remote.time.TimeReq
import com.behnamuix.nerkherooz.remote.timeModel.DateModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment() {
    private val gson = Gson()
    val sharedPreferences = context?.getSharedPreferences("user", Context.MODE_PRIVATE)
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private val goldPrice = ArrayList<ContentModel>()
    private val currencyPrice = ArrayList<ContentModel>()
    private val cryptoPrice = ArrayList<ContentModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        showSnack("لطفا دسترسی نوتیفیکیشن را جهت اطلاع رسانی به برنامه بدهید!")
        loadImageQuotes()

        val phone = sharedPreferences?.getString("phone", null)
        binding.tvPhone.text = phone.toString()

        binding.imgNotif.setOnClickListener() {
            showNotification(
                requireActivity(),
                "قابلیت های نسخه 1.3.0",
                "قابلیت های جدید به شرح زیر است:=\nبهبود رابط کاربری\nبرطرف کردن مشکلات عملکردی و منظقی"
            )
        }


        binding.tvDate.setOnClickListener() {
            //online
            getYear()
        }
        getPrice()
        binding.imageViewInfo.setOnClickListener() {
            showSnack("طراحی و توسعه:Ali mahjoob")
        }
        binding.tvGold.setTextColor(Color.parseColor("#A43669"))
        setTextViewDrawableColor(binding.tvGold, resources.getColor(R.color.teal_700))

        binding.tvCoin.setTextColor(Color.parseColor("#FF818181"))
        binding.tvCrypto.setTextColor(Color.parseColor("#FF818181"))
        setData(goldPrice)
        binding.tvGold.setOnClickListener() {

            binding.tvGold.setTextColor(Color.parseColor("#A43669"))
            setTextViewDrawableColor(binding.tvGold, resources.getColor(R.color.teal_700))

            binding.tvCoin.setTextColor(Color.parseColor("#FF818181"))
            binding.tvCrypto.setTextColor(Color.parseColor("#FF818181"))
            setData(goldPrice)


        }
        binding.tvCoin.setOnClickListener() {
            loadImageQuotes()
            binding.tvCoin.setTextColor(Color.parseColor("#A43669"))
            setTextViewDrawableColor(binding.tvCoin, resources.getColor(R.color.teal_700))


            binding.tvGold.setTextColor(Color.parseColor("#FF818181"))
            binding.tvCrypto.setTextColor(Color.parseColor("#FF818181"))
            setData(currencyPrice)
        }
        binding.tvCrypto.setOnClickListener() {
            binding.tvCrypto.setTextColor(Color.parseColor("#A43669"))
            setTextViewDrawableColor(binding.tvCrypto, resources.getColor(R.color.teal_700))

            binding.tvCoin.setTextColor(Color.parseColor("#FF818181"))
            binding.tvGold.setTextColor(Color.parseColor("#FF818181"))
            setData(cryptoPrice)
        }


        //offline
        //getYearOrHour()


        recyclerView = binding.recAds

        val adsList = listOf(
            Ads(0, "آدامس بایودنت", "https://behnamuix2024.com/img/tab1.jpg"),
            Ads(1, "فیلیموکودک", "https://behnamuix2024.com/img/tab2.jpg"),
            Ads(2, "رنگ مو رکسی", "https://behnamuix2024.com/img/tab3.jpg"),
            Ads(3, "کوکا کولا", "https://behnamuix2024.com/img/tab4.jpg"),
            Ads(4, "کوکا کولا در فروشگاه", "https://behnamuix2024.com/img/tab5.jpg"),
            // ...
        )

        val adsAdapter = AdsAdpater(adsList)
        recyclerView.adapter = adsAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        for (i in 0..4 step (1)) {
            Handler(Looper.getMainLooper()).postDelayed({
                recyclerView.smoothScrollToPosition(i)

            }, 10000)
        }

        val layoutManager2 = GridLayoutManager(activity, 2)
        binding.recyclerView.layoutManager = layoutManager2
        profile(phone)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun profile(phone: String?) {
        var phone="09307283313"
        val requestQueue = Volley.newRequestQueue(activity)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://behnamuix2024.com/api/getUser.php?phone=$phone",
            null,
            { response ->
                // تبدیل JSON به شیء User
                val profile = gson.fromJson(response.toString(), Profile::class.java)
                // نمایش داده‌ها
                Toast.makeText(activity,profile.name,Toast.LENGTH_LONG).show()
                binding.tvName.text = profile.name
                binding.tvPhone.text = profile.phone
            },
            { error ->
                // مدیریت خطا
                Log.e("2025AlphaError", error.toString())
            }
        )

        // اضافه کردن درخواست به صف درخواست‌ها
        requestQueue.add(jsonObjectRequest)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_channel_id"
            val channelName = "My Channel"
            val channelDescription = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // ساخت و نمایش نوتیفیکیشن
    fun showNotification(context: Context, title: String, message: String) {
        // ایجاد کانال نوتیفیکیشن
        createNotificationChannel(context)
        // ساخت نوتیفیکیشن
        val channelId = "my_channel_id"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon_next)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // نمایش نوتیفیکیشن
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(/*شناسه نوتیفیکشن*/ 0, notificationBuilder.build())
    }


    private fun loadImageQuotes() {
        val URl = "https://behnamuix2024.com/img/Warren_Buffett_Quotes.png"
        Glide
            .with(this)
            .load(URl)
            .centerCrop()
            .into(binding.imgQuotes)
    }

    private fun getYear() {
        TimeApiRepository.instance.getTime(object : TimeReq {
            override fun OnSuccess(date: DateModel) {
                val txt =
                    "${date.date.l_value} ${date.date.j_value} ${date.date.F_value} ${date.date.Y_value}"
                binding.tvDate.text = txt


            }

            override fun OnNotSuccess(msg: String) {
            }

            override fun OnError(err: String) {
            }

        })
    }


    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawablesRelative) {
            if (drawable != null) {
                drawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(textView.context, color), PorterDuff.Mode.SRC_IN
                )
            }
        }
    }

    fun getPrice() {
        binding.pbgRefresh.visibility = View.VISIBLE
        GoldApiRepository.instance.getGold(object : GoldReq {
            override fun OnSuccess(date: GoldModel) {
                goldPrice.addAll(date.data.golds)
                currencyPrice.addAll(date.data.currencies)
                cryptoPrice.addAll(date.data.cryptocurrencies)
                binding.tvGold.setTextColor(Color.parseColor("#A43669"))
                setTextViewDrawableColor(binding.tvGold, resources.getColor(R.color.teal_700))

                binding.tvCoin.setTextColor(Color.parseColor("#FF818181"))
                binding.tvCrypto.setTextColor(Color.parseColor("#FF818181"))
                setData(goldPrice)

                binding.pbgRefresh.visibility = View.GONE
            }

            override fun OnNotSuccess(msg: String) {
                Log.d("debug_Alpha", msg)
            }

            override fun OnError(err: String) {
                Log.d("debug_Alpha", err)
            }

        })
    }

    private fun setData(data: ArrayList<ContentModel>) {
        binding.recyclerView.adapter = RecyclerMainAdapter(data)


    }


    fun showSnack(str: String) {
        var snack = Snackbar.make(binding.frame, str, Snackbar.LENGTH_SHORT)
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams

        view.layoutParams = params
        snack.setBackgroundTint(resources.getColor(R.color.teal_700))
        snack.setTextColor(Color.WHITE)

        ViewCompat.setLayoutDirection(snack.view, ViewCompat.LAYOUT_DIRECTION_RTL)
        snack.show()
    }


}