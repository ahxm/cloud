package com.ans.cloud.client.io.reader;

import com.ans.cloud.client.exception.ClientException;
import com.ans.cloud.client.exception.ServerException;
import com.ans.cloud.client.http.HttpResponse;
import com.ans.cloud.model.Response;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by anzhen on 2016/6/9.
 */
public class JsonReader implements Reader {

    public static final String NULL = "null";
    private static final Object ARRAY_END_TOKEN = new Object();

    private static final Object OBJECT_END_TOKEN = new Object();
    private static final Object COMMA_TOKEN = new Object();
    private static final Object COLON_TOKEN = new Object();
    private static final int FIRST_POSITION = 0;
    private static final int CURRENT_POSITION = 1;
    private static final int NEXT_POSITION = 2;
    public static final String CODE = "code";
    public static final String REQUEST_ID = "requestId";
    public static final String MESSAGE = "message";
    private CharacterIterator ct;
    private char c;
    private Object token;
    private HttpResponse response;
    private StringBuffer stringBuffer = new StringBuffer();
    private Map<String, String> map = new HashMap<String, String>();
    private static ConcurrentMap<Class<?>, ConcurrentMap<String, PropertyDescriptor>> fields = new ConcurrentHashMap<Class<?>, ConcurrentMap<String, PropertyDescriptor>>();
    private static Map<Character, Character> escapes = new HashMap<Character, Character>();

    static {
        escapes.put(Character.valueOf('\\'), Character.valueOf('\\'));
        escapes.put(Character.valueOf('/'), Character.valueOf('/'));
        escapes.put(Character.valueOf('"'), Character.valueOf('"'));
        escapes.put(Character.valueOf('t'), Character.valueOf('\t'));
        escapes.put(Character.valueOf('n'), Character.valueOf('\n'));
        escapes.put(Character.valueOf('r'), Character.valueOf('\r'));
        escapes.put(Character.valueOf('b'), Character.valueOf('\b'));
        escapes.put(Character.valueOf('f'), Character.valueOf('\f'));
    }

    public JsonReader(HttpResponse response) {
        this.response = response;
    }

    @Override
    public Response read() throws ClientException, ServerException {
        return read(response.getContent(), response.getType(), "");
    }

    protected Response read(String response,Class type,String root) throws ClientException,ServerException {

        Map<String,String> proValues = read(response,root);
        String status = proValues.get(CODE);
        String requestId = proValues.get(REQUEST_ID);
        if(requestId !=null && !requestId.trim().isEmpty()){
            if(status!=null){
            throw new ServerException(proValues.get(REQUEST_ID),proValues.get(CODE),proValues.get(MESSAGE));
            }else{
                try {
                    Response responseObj = (Response)type.newInstance();
                    for (Map.Entry<String,String> proValue : proValues.entrySet()){
                        update(responseObj,proValue.getKey(),proValue.getValue());
                    }
                    return responseObj;
                } catch (InstantiationException e) {
                    throw new ClientException(String.format(
                            "Create instance[%s] error, has not a non-arg constructor?", type.getName()), e);
                } catch (IllegalAccessException e) {
                    throw new ClientException(String.format(
                            "Create instance[%s] error, The non-arg constructor is not public?", type.getName()),
                            e);
                } catch (Exception e) {
                    throw new ClientException(String.format(
                            "Create instance[%s] error", type.getName()), e);
                }
            }
        }else{
            // 没有接收到服务端的正常响应(网络节点等原因),抛异常重试
            throw new ClientException(String.format("Response[%s] is error", response), true);
        }


    }

    protected Map<String,String> read(String response,String endpoint) throws ClientException{

        try{
            return read(new StringCharacterIterator(response),endpoint,FIRST_POSITION);

        }catch (Throwable e){
            if(this.response.getStatus()>=400 && this.response.getStatus()<500){
                throw new ClientException(e);
            }else {
                throw new ClientException(e,true);
            }
        }

    }

    protected Map<String,String> read(CharacterIterator ci,String endpoint,int start) throws ClientException{
        ct = ci;
        switch (start){
            case FIRST_POSITION:
                c = ct.first();
                break;
            case CURRENT_POSITION:
                c = ct.current();
                break;
            case NEXT_POSITION:
                c = ct.next();
                break;
        }
        readJson(endpoint);
        return map;
    }

    private Object readJson(String baseKey){
        skipWhiteSpace();
        char ch = c;
        nextChar();
        switch (ch){
            case '{':
                processObject(baseKey);
                break;
            case '}':
                token = OBJECT_END_TOKEN;
                break;
            case '[':
                if(c == '"'){
                    processList(baseKey);
                    break;
                }else{
                    processArray(baseKey);
                    break;
                }
            case ']':
                token = ARRAY_END_TOKEN;
                break;
            case '"':
                token = processString();
                break;
            case ',':
                token = COMMA_TOKEN;
                break;
            case ':':
                token = COLON_TOKEN;
                break;
            case 't':
                nextChar();
                nextChar();
                nextChar();
                token = Boolean.TRUE;
                break;
            case 'n':
                nextChar();
                nextChar();
                nextChar();
                token = null;
                break;
            case 'f':
                nextChar();
                nextChar();
                nextChar();
                nextChar();
                token = Boolean.FALSE;
                break;
            default:
                c = ct.previous();
                if(Character.isDigit(c) || c == '-'){
                    token = processNumber();
                }
        }

        return token;
    }

    private boolean isList(final Class<?> clazz){
        return clazz == List.class;
    }

    public boolean update(final Object target,final String name,final String value) throws Exception{

        Object obj = target;
        char[] chars= name.toCharArray();
        String property;
        int index;
        PropertyDescriptor descriptor;
        Class<?> type;
        int pstart = -1;
        int pend = -1;
        int istart = -1;
        int iend = -1;
        int size = 0;
        char cur = 0;
        char pre;
        Object fv;
        List<Object> fvs;
        Class<?> clazz;
        Type fc;
        ParameterizedType pt;

    }

    private void skipWhiteSpace(){
        while (Character.isWhitespace(c)){
            nextChar();
        }
    }

    private char nextChar(){
        c = ct.next();
        return c;
    }

    private void processObject(String baseKey){

        String key = buildKey(baseKey,(String)readJson(baseKey));
        while (token != OBJECT_END_TOKEN){
            readJson(key);
            if(token != OBJECT_END_TOKEN){
                Object object = readJson(key);
                if(object instanceof String || object instanceof Number || object instanceof Boolean){
                    map.put(key,String.valueOf(object));
                }

                if(readJson(key) == COMMA_TOKEN){
                    key = String.valueOf(readJson(key));
                    key = buildKey(baseKey, key);
                }
            }
        }
    }

    private String buildKey(String parent, String current) {
        if (parent == null || parent.trim().isEmpty()) {
            return current;
        }

        return trimFromLast(parent, ".") + "." + current;
    }
    public static String trimFromLast(String str, String stripString) {
        int pos = str.lastIndexOf(stripString);
        if (pos > -1 && pos == str.length() -1) {
            return str.substring(0, pos);
        } else {
            return str;
        }
    }


    private void processList(String baseKey){

        Object value = readJson(baseKey);
        int index = 0;
        while(token!=ARRAY_END_TOKEN){
            String key = trimFromLast(baseKey,".")+"["+(index++)+"]";
            map.put(key,String.valueOf(value));
            if(readJson(baseKey) == COMMA_TOKEN){
                value = readJson(baseKey);
            }
        }
        map.put(trimFromLast(baseKey,".")+".Length",String.valueOf(index));
    }

    private void processArray(String baseKey){

        int index = 0;
        String preKey = trimFromLast(baseKey,".");
        String key = preKey+"["+index+"]";
        Object  value= readJson(key);
        while (token!=ARRAY_END_TOKEN){
            map.put(preKey+".Length",String.valueOf(index+1));
            if(value instanceof String){
                map.put(key,String.valueOf(value));
            }
            if(response == COMMA_TOKEN){
                key = preKey+"["+(++index)+"]";
                value = readJson(key);
            }
        }

    }

    private Object processString(){

        stringBuffer.setLength(0);
        while (c!='"'){
            if(c == '\\'){
                nextChar();
                Object value = escapes.get(Character.valueOf(c));
                if(value !=null){
                    addChar(((Character)value).charValue());
                }
            }else {
                addChar();
            }
        }

        nextChar();
        return stringBuffer.toString();

    }

    private Object processNumber(){

        stringBuffer.setLength(0);
        if('-' == c){
            addChar();
        }
        addDigits();
        if('.' == c){
            addChar();
            addDigits();
        }
        if('e' == c || 'E' == c){
            addChar();
            if('+' == c || '-' == c){
                addChar();
            }
            addDigits();
        }
        return stringBuffer.toString();
    }

    private void addDigits(){
        while (Character.isDigit(c)){
            addChar();
        }
    }

    private void addChar(char ch){
        stringBuffer.append(ch);
        nextChar();
    }

    private void addChar(){
        addChar(c);
    }


}
