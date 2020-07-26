package com.numny.mstarttask.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.numny.mstarttask.R;
import com.numny.mstarttask.database.models.DepartmentModel;
import com.numny.mstarttask.databinding.RowDepartemntsBinding;

import java.util.ArrayList;
import java.util.List;


public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.DepartmentViewHolder> {

    public interface OnDepartmentsAdapter {
        void onRowClicked(DepartmentModel departmentModel);
    }

    private List<DepartmentModel> mItems;
    private OnDepartmentsAdapter mListener;

    public DepartmentsAdapter(OnDepartmentsAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<>();
    }

    public void setItems(List<DepartmentModel> items) {

        if(items != null) {
            mItems.clear();
            mItems.addAll(items);
            notifyDataSetChanged();
        }
        else {
            mItems.clear();
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowDepartemntsBinding rowDepartemntsBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.row_departemnts, viewGroup, false);
        return new DepartmentViewHolder(rowDepartemntsBinding);
    }



    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder departmentViewHolder, int position) {
        DepartmentModel departmentModel = getItem(position);

        departmentViewHolder.rowDepartemntsBinding.setSingleDepartments(departmentModel);
        departmentViewHolder.setOnClickListener(departmentModel);
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private DepartmentModel getItem(int position) {
        return mItems.get(position);
    }


    class DepartmentViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        private RowDepartemntsBinding rowDepartemntsBinding;

        public DepartmentViewHolder(@NonNull RowDepartemntsBinding rowDepartemntsBinding) {
            super(rowDepartemntsBinding.getRoot());
            this.rowDepartemntsBinding = rowDepartemntsBinding;


        }

        private void setOnClickListener(DepartmentModel departmentModel) {
            itemView.setTag(departmentModel);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick (View view){
            mListener.onRowClicked((DepartmentModel) view.getTag());
        }
    }
}
