/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paramConverters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import paramTypes.VremenskiPeriod;


//Vraca ParamConverter-e za odredjene tipove.
@Provider
public class MyParamConverterProvider implements ParamConverterProvider{

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if(rawType.getName().equals(VremenskiPeriod.class.getName())){
            return new ParamConverter<T>() {
                @Override
                public T fromString(String value) {
                    try {
                        VremenskiPeriod vremenskiPeriod = new VremenskiPeriod();
                        
                        StringTokenizer st = new StringTokenizer(value, "-");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.");
                        String pocetak = st.nextToken();
                        String kraj = st.nextToken();
                        vremenskiPeriod.setDatumPocetka(sdf.parse(pocetak));
                        vremenskiPeriod.setDatumKraja(sdf.parse(kraj));
                        
                        return rawType.cast(vremenskiPeriod);
                    } catch (ParseException ex) {
                        Logger.getLogger(MyParamConverterProvider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }

                @Override
                public String toString(T value) {
                    if(value != null)
                        return value.toString();
                    return null;
                }
            };
        }
        
        return null;
    }
    
}
