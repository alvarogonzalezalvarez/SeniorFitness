package com.alvaro.seniorfitness.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.activities.InfoActivity;
import com.alvaro.seniorfitness.model.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    TextView name, result;
    ImageView photo, info;
    Test[] tests;
    Context context;
    String userId;
    String birthdate;
    String gender;

    public TestsAdapter(Context contexto, Test[] testsarray, String userId, String birthdate, String gender) {
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tests = testsarray;
        context = contexto;
        this.userId = userId;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    @Override
    public int getCount() {
        return tests.length;
    }

    @Override
    public Object getItem(int position) {
        return tests[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Test test = tests[position];
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.test_list_element, null);
        }

        name = (TextView) convertView.findViewById(R.id.name);
        result = (TextView) convertView.findViewById(R.id.result);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        info = (ImageView) convertView.findViewById(R.id.info);
        name.setText(test.getName());
        if (test.getResult() == null) {
            photo.setImageResource(R.drawable.pending);
            result.setText("-");
        } else {
            info.setVisibility(View.GONE);
            photo.setImageResource(R.drawable.check);
            String units = " repeticiones. ";
            if ("Agil".equals(test.getTestID())) {
                units = " segundos. ";
            } else if ("Flex_Pna".equals(test.getTestID()) || "Flex_Br".equals(test.getTestID())) {
                units = " centímetros. ";
            }

            Float resultFloat = Float.valueOf(test.getResult().replace(",","."));
            String intervalo = "";
            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();
            Integer age = 0;
            int factor = 0;
            try {
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
                Date date2 = new Date();
                cal1.setTime(date1);
                cal2.setTime(date2);
                if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
                    factor = -1;
                }
                age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;

                if ("Flex_Pna".equals(test.getTestID()) || "Flex_Br".equals(test.getTestID())) {
                    resultFloat = (float)resultFloat/2.54f;
                }

                if ("Mujer".equals(gender)) {
                    if (age >= 60 && age <= 64) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 12) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 17) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 13) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 19) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 75) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 107) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -0.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -3) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 1.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 6) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 4.4) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 65 && age <= 69) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 11) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 16) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 12) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 18) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 73) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 107) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -0.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 4.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -3.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 1.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 6.4) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 4.8) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 70 && age <= 74) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 15) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 12) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 17) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 68) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 101) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -1) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 4) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -4) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 1) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 7.1) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 4.9) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 75 && age <= 79) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 15) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 11) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 17) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 68) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 100) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -1.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 3.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 0.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 7.4) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 5.2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 80 && age <= 84) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 9) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 14) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 16) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 60) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 90) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -2) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 3) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -5.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 0) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 8.7) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 5.7) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 85 && age <= 89) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 8) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 13) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 15) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 55) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 85) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -2.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 2.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -7) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -1) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 9.6) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 6.2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 90 && age <= 94) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 4) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 11) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 8) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 13) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 44) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 72) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -4.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 1) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -8) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -1) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 11.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 7.3) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    }
                } else {
                    if (age >= 60 && age <= 64) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 14) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 19) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 16) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 22) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 87) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 115) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -2.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 4) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -6.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 0) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 5.6) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 3.8) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 65 && age <= 69) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 12) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 18) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 15) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 21) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 86) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 116) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -3) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 3) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -7.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -1) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 5.9) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 4.3) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 70 && age <= 74) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 12) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 17) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 14) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 21) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 80) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 110) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -3) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 3) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -8) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -1) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 6.2) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 4.4) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 75 && age <= 79) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 11) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 17) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 13) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 19) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 73) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 109) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -4) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -9) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 7.2) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 4.6) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 80 && age <= 84) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 15) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 13) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 19) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 71) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 103) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -5.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 1.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -9.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 7.6) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 5.2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 85 && age <= 89) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 8) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 14) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 11) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 17) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 59) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 91) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -5.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 0.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -9.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -3) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 8.9) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 5.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    } else if (age >= 90 && age <= 94) {
                        if ("F_Pna".equals(test.getTestID())) {
                            if (resultFloat < 7) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 12) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("F_Br".equals(test.getTestID())) {
                            if (resultFloat < 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 14) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Resist".equals(test.getTestID())) {
                            if (resultFloat < 52) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > 86) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Pna".equals(test.getTestID())) {
                            if (resultFloat < -6.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -0.5) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Flex_Br".equals(test.getTestID())) {
                            if (resultFloat < -10.5) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat > -4) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        } else if ("Agil".equals(test.getTestID())) {
                            if (resultFloat > 10) {
                                intervalo = "Por debajo del intervalo normal";
                            } else if (resultFloat < 6.2) {
                                intervalo = "Por encima del intervalo normal";
                            } else {
                                intervalo = "Dentro del intervalo normal";
                            }
                        }
                    }
                }
            } catch (ParseException e) {}

            result.setText(test.getResult() + units + intervalo);
        }
        photo.setScaleType(ImageView.ScaleType.FIT_END);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("testId", test.getTestID());
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
