package com.example.fazaksiyca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    EditText er, em, ed, eg, emin, es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        er = findViewById(R.id.erok);
        em = findViewById(R.id.emiesiac);
        ed = findViewById(R.id.edzien);
        eg = findViewById(R.id.egodzina);
        emin = findViewById(R.id.eminuta);
        es = findViewById(R.id.esekunda);
        tv.setText("Wynik dodatni (prawa strona księżyca oświetlona - elongacja dodatnia)\r\n" +
                "Wynik ujemny (lewa strona księżyca oświetlona - elongacja ujemna)\r\n" +
                "Nów        =   0.00\r\n" +
                "I kwadra   =  50.00\r\n" +
                "Pełnia     = 100.00\r\n" +
                "III kwadra = -50.00");
    }

    public void wyznacz(View view) {
        String bufor="Wynik dodatni (prawa strona księżyca oświetlona - elongacja dodatnia)\r\n" +
                "Wynik ujemny (lewa strona księżyca oświetlona - elongacja ujemna)\r\n" +
                "Nów        =   0.00\r\n" +
                "I kwadra   =  50.00\r\n" +
                "Pełnia     = 100.00\r\n" +
                "III kwadra = -50.00";
        try {
            bufor+="\r\n\r\n";
            double rok = Double.parseDouble(er.getText().toString());
            if(er.getText().toString()=="") rok=0;
            double miesiac = Double.parseDouble(em.getText().toString());
            if(em.getText().toString()=="") miesiac=0;
            if(miesiac<1) miesiac=1;
            else if(miesiac>12) miesiac=12;
            double dzien = Double.parseDouble(ed.getText().toString());
            if(ed.getText().toString()=="") dzien=0;
            if(dzien<1) dzien=1;
            else if(dzien>31 && (miesiac==1 || miesiac==3 || miesiac==5 || miesiac==7 || miesiac==8 || miesiac==10 ||
                    miesiac==12)) dzien=31;
            else if(dzien>30 && (miesiac==4 || miesiac==6 || miesiac==9 || miesiac==11)) dzien=30;
            else if(miesiac==2 && dzien>29 && ((rok%4==0 && rok%100!=0)|| rok%400==0)) dzien=29;
            else if((miesiac==2 && dzien>28 && (!(rok%4==0 && rok%100!=0)|| rok%400==0))) dzien=28;
            double godzina = 0, minuta = 0, sekunda = 0;
            godzina = Double.parseDouble(eg.getText().toString());
            if(eg.getText().toString()=="") godzina=0;
            if(godzina<0 || godzina>23) godzina=0;
            minuta=Double.parseDouble(emin.getText().toString());
            if(emin.getText().toString()=="") minuta=0;
            if(minuta<0 || minuta>59) minuta=0;
            sekunda=Double.parseDouble(es.getText().toString());
            if(es.getText().toString()=="") sekunda=0;
            if(sekunda<0 || sekunda>59) sekunda=0;
            bufor+=""+(int)rok+"-";
            if(miesiac<10) bufor+="0";
            bufor+=(int)miesiac+"-";
            if(dzien<10) bufor+="0";
            bufor+=(int)dzien+". ";
            if(godzina<10) bufor+="0";
            bufor+=(int)godzina+":";
            if(minuta<10) bufor+="0";
            bufor+=(int)minuta+":";
            if(sekunda<10) bufor+="0";
            bufor+=(int)sekunda+"\r\n";
            bufor+="Faza księżyca: "+faza(rok,miesiac,dzien,godzina,minuta,sekunda);
        }catch (Exception blad){
            Toast.makeText(this,blad.getMessage(),Toast.LENGTH_LONG).show();
        }finally {
            tv.setText(bufor);
            er.setText("");
            em.setText("");
            ed.setText("");
            eg.setText("");
            emin.setText("");
            es.setText("");
        }
    }

    public double rang(double x) {
        double a,b;
        b=x/360;
        a=360*(b-(int)b);
        if(a<0) a+=360;
        return a;
    }

    public double faza(double Rok, double Miesiac, double Dzien, double godzina,double min, double sec) {
        double A;
        double b;
        double phi1, phi2, jdp, tzd, elm, ams, aml, asd;


        if(Miesiac <=2) {
            Miesiac+=12;
            Rok-=1;
        }

        A= (int)(Rok / 100);
        b=2 - A + (int)(A / 4);
        jdp= (int)(365.25 * (Rok + 4716)) + (int)(30.6001 * (Miesiac + 1)) + Dzien + b +
                ((godzina + min / 60 + sec / 3600) / 24) - 1524.5;
        tzd=(jdp - 2451545) / 36525;
        elm=
        rang(297.8502042 + 445267.1115168 * tzd - (0.00163 * tzd * tzd) + tzd * tzd * tzd / 545868 - tzd * tzd * tzd * tzd / 113065000);
        ams=
        rang(357.5291092 + 35999.0502909 * tzd - 0.0001536 * tzd * tzd + tzd * tzd * tzd / 24490000);
        aml=
        rang(134.9634114 + 477198.8676313 * tzd - 0.008997 * tzd * tzd + tzd * tzd * tzd / 69699 - tzd * tzd * tzd * tzd / 14712000);
        asd=180 - elm - (6.289 * Math.sin((3.1415926535 / 180) * ((aml)))) +
                (2.1 * Math.sin((3.1415926535 / 180) * ((ams)))) -
                (1.274 * Math.sin((3.1415926535 / 180) * (((2 * elm) - aml)))) -
                (0.658 * Math.sin((3.1415926535 / 180) * ((2 * elm)))) -
                (0.214 * Math.sin((3.1415926535 / 180) * ((2 * aml)))) -
                (0.11 * Math.sin((3.1415926535 / 180) * ((elm))));
        phi1=(1 + Math.cos((3.1415926535 / 180) * (asd))) / 2;


        tzd=(jdp + (0.5 / 24) - 2451545) / 36525;
        elm=
        rang(297.8502042 + 445267.1115168 * tzd - (0.00163 * tzd * tzd) + tzd * tzd * tzd / 545868 - tzd * tzd * tzd * tzd / 113065000);
        ams=
        rang(357.5291092 + 35999.0502909 * tzd - 0.0001536 * tzd * tzd + tzd * tzd * tzd / 24490000);
        aml=
        rang(134.9634114 + 477198.8676313 * tzd - 0.008997 * tzd * tzd + tzd * tzd * tzd / 69699 - tzd * tzd * tzd * tzd / 14712000);
        asd=180 - elm - (6.289 * Math.sin((3.1415926535 / 180) * ((aml)))) +
                (2.1 * Math.sin((3.1415926535 / 180) * ((ams)))) -
                (1.274 * Math.sin((3.1415926535 / 180) * (((2 * elm) - aml)))) -
                (0.658 * Math.sin((3.1415926535 / 180) * ((2 * elm)))) -
                (0.214 * Math.sin((3.1415926535 / 180) * ((2 * aml)))) -
                (0.11 * Math.sin((3.1415926535 / 180) * ((elm))));
        phi2=(1 + Math.cos((3.1415926535 / 180) * (asd))) / 2;


        if ((phi2 - phi1)<0) phi1=-1 * phi1;
        return 100 * phi1;
    }

}
