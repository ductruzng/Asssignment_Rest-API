package trungndph39729.fpoly.assignment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import trungndph39729.fpoly.assignment.Domain.Order;
import trungndph39729.fpoly.assignment.databinding.ViewholderOrderBinding;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> {
    ArrayList<Order> items;

    Context context;

    public OrderAdapter(ArrayList<Order> items,Context context) {
        this.items = items;
        this.context =  context;
    }

    @NonNull
    @Override
    public OrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderOrderBinding binding = ViewholderOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.Viewholder holder, int position) {
        Order order = items.get(position);
        holder.binding.dateTxt.setText(getDate(order.getCreatedAt()));
        holder.binding.titleTxt.setText(order.getProducts().get(0).getTitle());
        holder.binding.countTxt.setText("Quantity: "+order.getProducts().get(0).getNumberInCart());
        holder.binding.totalPriceTxt.setText("Total Price:"+order.getTotalPrice()+"$");
        Glide.with(holder.itemView.getContext())
                .load(order.getProducts().get(0).getPicUrl().get(0))
                .into(holder.binding.pic);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderOrderBinding binding;

        public Viewholder(@NonNull ViewholderOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private String getDate(String dateTimeStr) {
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date date = sdf.parse(dateTimeStr);

            SimpleDateFormat sdfDayOfWeek = new SimpleDateFormat("EEEE");
            SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
            SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

            String dayOfWeek = sdfDayOfWeek.format(date);
            String day = sdfDay.format(date);
            String month = sdfMonth.format(date);
            String year = sdfYear.format(date);
            d = dayOfWeek + ", " + day + "/" + month + "/" + year;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}
