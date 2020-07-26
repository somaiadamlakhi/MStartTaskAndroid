package com.numny.mstarttask.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.EmployeeModel;
import com.numny.mstarttask.databinding.RowEmployeeBinding;


import java.util.ArrayList;
import java.util.List;


public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeesViewHolder> {

    public interface OnEmployeeAdapter {
        void onRowClicked(EmployeeModel employeeModel);
    }

    private List<EmployeeModel> mItems;
    private OnEmployeeAdapter mListener;

    public EmployeesAdapter(OnEmployeeAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<EmployeeModel> items) {
        int lastPosition = items.size();
//        mItems.clear();
        mItems = items;
//        mItems.addAll(items);
        notifyItemRangeChanged(lastPosition, mItems.size());
    }


    @NonNull
    @Override
    public EmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowEmployeeBinding rowEmployeeBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.row_employee, viewGroup, false);
        return new EmployeesViewHolder(rowEmployeeBinding);
    }



    @Override
    public void onBindViewHolder(@NonNull EmployeesViewHolder employeesViewHolder, int position) {
        EmployeeModel employeeModel = getItem(position);

        employeesViewHolder.rowEmployeeBinding.setSingleEmployee(employeeModel);
        employeesViewHolder.setOnClickListener(employeeModel);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private EmployeeModel getItem(int position) {
        return mItems.get(position);
    }


    class EmployeesViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private RowEmployeeBinding rowEmployeeBinding;

        public EmployeesViewHolder(@NonNull RowEmployeeBinding rowEmployeeBinding) {
            super(rowEmployeeBinding.getRoot());
            this.rowEmployeeBinding = rowEmployeeBinding;


        }

        private void setOnClickListener(EmployeeModel employeeModel) {
            itemView.setTag(employeeModel);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick (View view){
            mListener.onRowClicked((EmployeeModel) view.getTag());
        }
    }
}
