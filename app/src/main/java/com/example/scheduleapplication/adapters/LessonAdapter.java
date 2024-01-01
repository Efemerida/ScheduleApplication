package com.example.scheduleapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapplication.R;
import com.example.scheduleapplication.entites.Lesson;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder>{

    private LayoutInflater inflater;
    private List<Lesson> lessons;


    public LessonAdapter(Context context, List<Lesson> lessons) {
        this.inflater = LayoutInflater.from(context);
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonAdapter.LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_list, parent, false);
        return new LessonAdapter.LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.course.setText(lesson.getCourse());
        holder.office.setText(lesson.getOffice());
        holder.teacher.setText(lesson.getTeacher());
        holder.time.setText(lesson.getTime());
        holder.type.setText(lesson.getType());

    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder {

        final TextView time, office, officeM2, course, teacher, type;

        public LessonViewHolder(@NonNull View view){
            super(view);
            time=(TextView) view.findViewById(R.id.hour_1);
            office= (TextView) view.findViewById(R.id.room_1);
            officeM2 =(TextView) view.findViewById(R.id.room2_1);
            course = (TextView) view.findViewById(R.id.name_1);
            teacher = (TextView) view.findViewById(R.id.teacher_1);
            type = (TextView) view.findViewById(R.id.type_1);
        }
    }

}
