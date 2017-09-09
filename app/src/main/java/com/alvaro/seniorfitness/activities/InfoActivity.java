package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;
import com.alvaro.seniorfitness.model.Test;

import java.util.HashMap;
import java.util.Map;


public class InfoActivity extends AppCompatActivity {

    Map<String, Test> tests;
    String userId;
    String testId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userId = getIntent().getStringExtra("userId");
        testId = getIntent().getStringExtra("testId");

        tests = new HashMap<String, Test>();
        tests.put("F_Pna",new Test("F_Pna","Fuerza de Piernas","Número de veces que es capaz de " +
                "sentarse y levantarse de una silla con los brazos en cruz y colocados sobre el pecho",
                null, R.drawable.fuerzapiernas));
        tests.put("F_Br",new Test("F_Br","Fuerza de Brazos","Número de flexiones de brazo completas, " +
                "sentado en una silla, que realiza sujetando una pesa de 3 libras (2,27 kg) para " +
                "mujeres y 5 libras (3,63 kg) para hombres", null, R.drawable.fuerzabrazos));
        tests.put("Resist",new Test("Resist","Resistencia Aeróbica","Número de veces que levanta la rodilla " +
                "hasta una altura equivalente al punto medio entre la rótula y la cresta ilíaca",
                null, R.drawable.resistenciaaerobica));
        tests.put("Flex_Pna",new Test("Flex_Pna","Flexibilidad de Piernas","Sentado en el borde de una silla, estirar " +
                "la pierna e intentar alcanzar con las manos los dedos del pie, que está con una flexión de tobillo " +
                "de 90 grados. Se mide la distancia entre la punta de los dedos de la mano y la punta " +
                "del pie (positiva si los dedos de la mano sobrepasan los dedos del pie o negativa si los dedos de " +
                "la mano no alcanzan a tocar los dedos del pie)",
                null, R.drawable.flexibilidadpiernas));
        tests.put("Flex_Br",new Test("Flex_Br","Flexibilidad de Brazos","Una mano se pasa por encima del mismo hombro y la " +
                "otra pasa a tocar la parte media de la espalda intentando que ambas manos se toquen. Se mide la " +
                "distancia entre la punta de los dedos de cada mano (positiva si los dedos de la mano se " +
                "superponen o negativa si no llegan a tocarse los dedos de la mano)",
                null, R.drawable.flexibilidadbrazos));
        tests.put("Agil",new Test("Agil","Agilidad","Partiendo de sentado, tiempo que tarda en levantarse " +
                "caminar hasta un cono situado a 2,44 metros, girar y volver a sentarse", null,
                R.drawable.agilidad));

        String howToPierna = "Colocar el dispositivo sobre el muslo. " +
                "Debe quedar colocado con la pantalla hacia arriba y de forma que sea legible desde " +
                "el punto de partida del ejercicio.";
        String howToBrazo = "Colocar el dispositivo sobre el antebrazo. Debe quedar colocado con la pantalla " +
                "hacia arriba y de forma que sea legible desde el punto de partida del ejercicio.";

        setTitle(tests.get(testId).getName());
        ImageView pierna = (ImageView) findViewById(R.id.legimage);
        pierna.setVisibility(View.GONE);
        ImageView brazo = (ImageView) findViewById(R.id.armimage);
        brazo.setVisibility(View.GONE);
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(tests.get(testId).getDescription());
        TextView howto = (TextView) findViewById(R.id.howto);
        howto.setVisibility(View.GONE);
        if ("F_Pna".equals(testId) || "Resist".equals(testId)) {
            howto.setText(howToPierna);
            howto.setVisibility(View.VISIBLE);
            pierna.setVisibility(View.VISIBLE);
        } else  if ("F_Br".equals(testId)) {
            howto.setText(howToBrazo);
            howto.setVisibility(View.VISIBLE);
            brazo.setVisibility(View.VISIBLE);
        }
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(tests.get(testId).getImage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("userId", userId);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}