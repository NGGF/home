package tw.tpe.com.nggf.sso.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 標題： String trim
 */
public class TrimInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = System.currentTimeMillis();
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
		
		Set entrySet = parameters.entrySet();  
        String[] strings = null;  
        String[] values = null;  
        int strLength = 0;
        
        for (Iterator it = entrySet.iterator(); it.hasNext();) {  
            Entry entry = (Entry) it.next();  
            Object key = entry.getKey();  
            Object value = entry.getValue();  
  
            if (value instanceof String[]) {  
                values = (String[]) value;  
                strLength = values.length;  
                strings = new String[strLength];
                for (int i = 0; i < strLength; i++) {  
                    strings[i] = values[i].trim();  
                }
                parameters.put((String) key, strings);  
            }  
        }  
          
        invocation.getInvocationContext().setParameters(parameters);  
        invocation.invoke();  
        return null;
	}
}
