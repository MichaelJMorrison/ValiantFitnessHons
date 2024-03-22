package honours.application.valiantfitness.chartformat;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import honours.application.valiantfitness.trackerdata.TrackerData;

//https://stackoverflow.com/questions/41426021/how-to-add-x-axis-as-datetime-label-in-mpandroidchart Reference
public class LineAxisXFormatDate extends IndexAxisValueFormatter {

    List<String> trackerDataList;

    public LineAxisXFormatDate(List<String> trackerDataList) {

        this.trackerDataList = trackerDataList;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int position = (int) value;

        return trackerDataList.get(position);
    }
}
