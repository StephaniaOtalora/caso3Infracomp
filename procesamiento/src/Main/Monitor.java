package Main;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Monitor extends Thread{
	String filename = "./docs/rendimiento.txt";
	ArrayList<String> rendimientos = new ArrayList<>();
	boolean apagar=false;
	@Override
	public void run() {

		while(!apagar) {
			try {
				Date date = Calendar.getInstance().getTime();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String strDate = dateFormat.format(date);  
				rendimientos.add(strDate+": "+(getSystemCpuLoad()+""));
				Thread.sleep(300000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public double getSystemCpuLoad() throws Exception {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		AttributeList list = mbs.getAttributes(name, new String[]{ "SystemCpuLoad" });
		if (list.isEmpty()) return Double.NaN;
		Attribute att = (Attribute)list.get(0);
		Double value = (Double)att.getValue();
		// usually takes a couple of seconds before we get real values
		if (value == -1.0) return Double.NaN;
		// returns a percentage value with 1 decimal point precision
		return ((int)(value * 1000) / 10.0);
	}
	public void setApagar(boolean p) {
		this.apagar=p;
	}
	public ArrayList<String> getRendimientos() {
		return rendimientos;
	}

	public void setRendimientos(ArrayList<String> rendimientos) {
		this.rendimientos = rendimientos;
	}
	
}
