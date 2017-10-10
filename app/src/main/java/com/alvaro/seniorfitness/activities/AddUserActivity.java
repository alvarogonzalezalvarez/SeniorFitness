package com.alvaro.seniorfitness.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddUserActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private String userChoosenTask;
    private ImageView photo;
    private final Activity these = this;
    private String imagePath;
    private Uri picUri;
    private TextView nullText;
    private TextView invalidDni;
    private TextView invalidDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Alta de Persona");
        dbHelper = new SeniorFitnessDBHelper(this);

        nullText = (TextView) findViewById(R.id.nulltext);
        nullText.setVisibility(View.GONE);
        invalidDni = (TextView) findViewById(R.id.invaliddni);
        invalidDni.setVisibility(View.GONE);
        invalidDate = (TextView) findViewById(R.id.invaliddate);
        invalidDate.setVisibility(View.GONE);
        photo = (ImageView) findViewById(R.id.thePhoto);
        final Button saveButton = (Button) findViewById(R.id.save);
        final Button photoButton = (Button) findViewById(R.id.photo);
        final EditText userid = (EditText) findViewById(R.id.userid);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText lastname = (EditText) findViewById(R.id.lastname);
        final EditText gender = (EditText) findViewById(R.id.gender);
        final EditText birthdate = (EditText) findViewById(R.id.birthdate);
        final RadioGroup genderRadioGroup = (RadioGroup) findViewById(R.id.gender_radio_group);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthdateString = birthdate.getText().toString();
                boolean validForm = true;
                invalidDate.setVisibility(View.GONE);
                nullText.setVisibility(View.GONE);
                invalidDni.setVisibility(View.GONE);
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha = sdf.parse(birthdate.getText().toString());
                    birthdateString = sdf.format(fecha);
                } catch (ParseException e) {
                    validForm = false;
                    invalidDate.setVisibility(View.VISIBLE);
                }
                int selectedId = genderRadioGroup.getCheckedRadioButtonId();
                String gender;
                if(selectedId == R.id.female_radio_btn)
                    gender = "Mujer";
                else
                    gender = "Hombre";
                if ("".equals(userid.getText().toString()) || "".equals(name.getText().toString()) ||
                        "".equals(lastname.getText().toString()) || "".equals(birthdateString)) {
                    validForm = false;
                    nullText.setVisibility(View.VISIBLE);
                }
                if (!validarDni(userid.getText().toString())) {
                    validForm = false;
                    invalidDni.setVisibility(View.VISIBLE);
                }
                if (validForm) {
                    new insertPerson().execute(userid.getText().toString(),
                            name.getText().toString(), lastname.getText().toString(),
                            gender, birthdateString, imagePath);
                    NavUtils.navigateUpFromSameTask(these);
                }
            }
        });
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(these, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(these, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(these, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(these, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                } else {
                    selectPhoto();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class insertPerson extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... what) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Mapa de valores, cuyas claves serán los nombres de las columnas
            ContentValues values = new ContentValues();
            values.put(SeniorFitnessContract.User.COLUMN_NAME_USERID, what[0]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_NAME, what[1]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_LASTNAME, what[2]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_GENDER, what[3]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_BIRTHDATE, what[4]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_PHOTO, what[5]);

            long newRowId = db.insert(SeniorFitnessContract.User.TABLE_NAME, null, values);
            return newRowId;
        }

        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(getApplicationContext(), "Usuario registrado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPhoto() {
        final CharSequence[] items = { "Tomar Foto", "Seleccionar Foto",
                "Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddUserActivity.this);
        builder.setTitle("Añadir Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Tomar Foto")) {
                    userChoosenTask="Tomar Foto";
                    cameraIntent();
                } else if (items[item].equals("Seleccionar Foto")) {
                    userChoosenTask="Seleccionar Foto";
                    galleryIntent();
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        picUri = Uri.fromFile(destination);
        imagePath = destination.getAbsolutePath();
        intent.putExtra( MediaStore.EXTRA_OUTPUT, picUri );
        startActivityForResult(intent, 0);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        picUri = Uri.fromFile(destination);
        imagePath = destination.getAbsolutePath();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1)
                onSelectFromGalleryResult(data);
            else if (requestCode == 0)
                performCrop();
            else if (requestCode == 2)
                onCropImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                File theImage = new File(data.getData().getPath());
                File theCopy = new File(picUri.getPath());

                FileChannel source = null;
                FileChannel destination = null;

                try {
                    source = new FileInputStream(theImage).getChannel();
                    destination = new FileOutputStream(theCopy).getChannel();
                    destination.transferFrom(source, 0, source.size());
                } finally {
                    if (source != null) {
                        source.close();
                    }
                    if (destination != null) {
                        destination.close();
                    }
                }

                performCrop();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onCropImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(picUri.getPath());
        FileOutputStream fo;
        try {
            if (destination.exists()) {
                destination.delete();
            }
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        photo.setImageBitmap(thumbnail);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                selectPhoto();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 2);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast toast = Toast
                    .makeText(this, "This device doesn't support the crop action!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean validarDni(String dni) {
        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula

        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false ) {
            return false;
        }

        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (dni.substring(8)).toUpperCase();

        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(soloNumeros(dni) == true && letraDNI(dni).equals(letraMayuscula)) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean soloNumeros(String dni) {
        int i, j = 0;
        String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
        String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
        String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};

        for(i = 0; i < dni.length() - 1; i++) {
            numero = dni.substring(i, i+1);

            for(j = 0; j < unoNueve.length; j++) {
                if(numero.equals(unoNueve[j])) {
                    miDNI += unoNueve[j];
                }
            }
        }

        if(miDNI.length() != 8) {
            return false;
        }
        else {
            return true;
        }
    }

    private String letraDNI(String dni) {
        // El método es privado porque lo voy a usar internamente en esta clase, no se necesita fuera de ella
        // pasar miNumero a integer
        int miDNI = Integer.parseInt(dni.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
        resto = miDNI % 23;
        miLetra = asignacionLetra[resto];
        return miLetra;
    }

}