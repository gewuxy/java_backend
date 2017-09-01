package cn.medcn.transfer.email;

import cn.medcn.transfer.model.AppUserUserNameBean;
import cn.medcn.transfer.support.Pageable;
import cn.medcn.transfer.utils.CommonConnctionUtils;
import cn.medcn.transfer.utils.DAOUtils;
import cn.medcn.transfer.utils.LogUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lixuan on 2017/7/17.
 */
public class EmailHelper {

    private static final int PAGE_SIZE = 500;

    public static String MAIL_SERVER_HOST = "smtp.qiye.163.com";
    public static String MAIL_USER_NAME = "service@medcn.cn";
    public static String MAIL_PASS_WORD = "JINGXIN@88";
    public static int MAIL_SERVER_PORT = 25;
    public static String COMPANY_NAME = "敬信药草园";
    private static String MAIL_SUBJECT = "【YaYa医师升级啦】花60秒更新版本，享受更好的服务";

    private static Properties props ;

    static {
        props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", MAIL_SERVER_HOST);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
    }

    private static String MAIL_CONTENT = "<p>" +
            "                    <span style=\"font-size: 16px;font-family:&#39;等线 Light&#39;\">尊敬的YaYa医师用户：</span>" +
            "                </p>" +
            "                <p style=\"text-indent: 32px\">" +
            "                    <span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">感谢您对YaYa医师的支持与信赖！</span>" +
            "                </p>" +
            "                <p style=\"text-indent: 32px\">" +
            "                    <span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">从2013到2017，从第一位用户、第一个单位号到如今70万医师和千家单位，YaYa医师团队始终在产品功能、用户体验和服务上精益求精，致力打造最专业的医学教育培训平台。</span>" +
            "                </p>" +
            "                <p style=\"text-indent: 32px\">" +
            "                    <span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">2017</span><span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">年7月10日，YaYa医师7.0版本全新上线！！！</span>" +
            "                </p>" +
            "                <p style=\"text-indent: 32px\">" +
            "                    <strong><span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">与此同时，7.0之前的版本将于7月31日全面停止数据更新服务，为了不影响您的使用，请您及时前往各大应用商店进行更新。</span></strong>" +
            "                </p>" +
            "                <p style=\"text-indent: 32px\">" +
            "                    <span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">&nbsp;</span>" +
            "                </p>" +
            "                <p style=\"text-align:right\">" +
            "                    <a><span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">YaYa</span></a><span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">医师团队</span>" +
            "                </p>" +
            "                <p style=\"text-align:right\">" +
            "                    <span style=\"font-size:16px;font-family: &#39;等线 Light&#39;\">敬信科技</span>" +
            "                </p>" +
            "                <p style=\"text-align:right\">" +
            "                    <span style=\"font-size:16px;font-family:&#39;等线 Light&#39;\">@Date</span>" +
            "                </p>";




    public static void sendToUsers() throws IntrospectionException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String sql = "select u.username as username from t_app_user u INNER JOIN t_meet_attend a on a.user_id = u.id and u.token is null and u.role_id=2 ORDER BY a.start_time desc limit ?, ?";
        int startPage ;
        for(int i = 8; i < 1000; i++){
            startPage = i*PAGE_SIZE;
            LogUtils.debug(EmailHelper.class, "start send email to no ["+(i+1)+"] page users...");
            List<AppUserUserNameBean> list = (List<AppUserUserNameBean>) DAOUtils.selectList(CommonConnctionUtils.getServerConnection(), sql, new Object[]{startPage, PAGE_SIZE}, AppUserUserNameBean.class);
            if(list.isEmpty()){
                LogUtils.debug(EmailHelper.class, "Send email to users finished !!!");
                break;
            }
            //removeInalidEmail(list);
            send(list);
            LogUtils.debug(EmailHelper.class, "Send email to no["+(i+1)+"] page users success !!!");
        }

    }

    private static InternetAddress[] userNamesToAddressArray(List<AppUserUserNameBean> list){
        InternetAddress[] addresses = new InternetAddress[list.size()];
        for(int i=0; i< list.size(); i++){
            AppUserUserNameBean userUserNameBean = list.get(i);
            try {
                addresses[i] = new InternetAddress(userUserNameBean.getUsername(), userUserNameBean.getUsername(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return addresses;
    }

    public static void send(List<AppUserUserNameBean> list){
        Session session = Session.getDefaultInstance(props);
        Transport transport = null;
        try {
            transport = session.getTransport();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            transport.connect(MAIL_USER_NAME, MAIL_PASS_WORD);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MimeMessage message = null;
        try {
            message = createMimeMessage(session, MAIL_USER_NAME, userNamesToAddressArray(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
            LogUtils.error(EmailHelper.class, "Send Email Error !!!");
        }
        try {
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static MimeMessage createMimeMessage(Session session, String sendMail, InternetAddress[] receiveMails) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, COMPANY_NAME, "UTF-8"));
        message.setRecipients(MimeMessage.RecipientType.TO, receiveMails);
        message.setSubject(MAIL_SUBJECT, "UTF-8");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        message.setContent(MAIL_CONTENT.replace("@Date", format.format(new Date())), "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }


    private static String normalizeAddress(String addr)
    {
        if ((!addr.startsWith("<")) && (!addr.endsWith(">")))
            return "<" + addr + ">";
        else
            return addr;
    }

    public static boolean isAddressValid( String address ) {
        // Find the separator for the domain name
        int pos = address.indexOf( '@' );

        // If the address does not contain an '@', it's not valid
        if ( pos == -1 ) return false;

        // Isolate the domain/machine name and get a list of mail exchangers
        String domain = address.substring( ++pos );
        List mxList = null;
        try {
            mxList = getMX( domain );
        }
        catch (NamingException ex) {
            return false;
        }

        if ( mxList.size() == 0 ) return false;
        boolean valid = false;
        for ( int mx = 0 ; mx < mxList.size() ; mx++ ) {
            try {
                int res;
                //
                Socket skt = new Socket( (String) mxList.get( mx ), 25 );
                BufferedReader rdr = new BufferedReader
                        ( new InputStreamReader( skt.getInputStream() ) );
                BufferedWriter wtr = new BufferedWriter
                        ( new OutputStreamWriter( skt.getOutputStream() ) );

                res = hear( rdr );
                if ( res != 220 ) throw new Exception( "Invalid header" );
                say( wtr, "EHLO rgagnon.com" );

                res = hear( rdr );
                if ( res != 250 ) throw new Exception( "Not ESMTP" );

                // validate the sender address
                say( wtr, "MAIL FROM: <tim@orbaker.com>" );
                res = hear( rdr );
                if ( res != 250 ) throw new Exception( "Sender rejected" );

                say( wtr, "RCPT TO: <" + address + ">" );
                res = hear( rdr );

                // be polite
                say( wtr, "RSET" ); hear( rdr );
                say( wtr, "QUIT" ); hear( rdr );
                if ( res != 250 )
                    throw new Exception( "Address is not valid!" );

                valid = true;
                rdr.close();
                wtr.close();
                skt.close();
                break;
            }
            catch (Exception ex) {
                LogUtils.error(EmailHelper.class, address+" : "+ex.getMessage());
            }
        }
        return valid;
    }

    private static int hear( BufferedReader in ) throws IOException {
        String line = null;
        int res = 0;

        while ( (line = in.readLine()) != null ) {
            String pfx = line.substring( 0, 3 );
            try {
                res = Integer.parseInt( pfx );
            }
            catch (Exception ex) {
                res = -1;
            }
            if ( line.charAt( 3 ) != '-' ) break;
        }

        return res;
    }

    private static void say(BufferedWriter wr, String text )
            throws IOException {
        wr.write( text + "\r\n" );
        wr.flush();

        return;
    }

    public static List getMX(String hostName)
            throws NamingException {
        Hashtable env = new Hashtable();
        env.put("java.naming.factory.initial",
                "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );
        Attributes attrs = ictx.getAttributes
                ( hostName, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );

        if (( attr == null ) || ( attr.size() == 0 )) {
            attrs = ictx.getAttributes( hostName, new String[] { "A" });
            attr = attrs.get( "A" );
            if( attr == null )
                throw new NamingException
                        ( "No match for name '" + hostName + "'" );
        }
        ArrayList res = new ArrayList();
        NamingEnumeration en = attr.getAll();

        while ( en.hasMore() ) {
            String mailhost;
            String x = (String) en.next();
            String f[] = x.split( " " );
            if (f.length == 1)
                mailhost = f[0];
            else if ( f[1].endsWith( "." ) )
                mailhost = f[1].substring( 0, (f[1].length() - 1));
            else
                mailhost = f[1];
            res.add( mailhost );
        }
        return res;
    }


    public static void removeInalidEmail(List<AppUserUserNameBean> userNameBeans){
        Iterator<AppUserUserNameBean> iterator = userNameBeans.iterator();
        while(iterator.hasNext()){
            AppUserUserNameBean email = iterator.next();
            if(!isAddressValid(email.getUsername())){
                iterator.remove();
            }
        }
    }


    public static void main(String[] args) {
//        List<String> userNames = new ArrayList<>();
//        userNames.add("18194529@qq.com");
//        userNames.add("584479243@qq.com");
//        userNames.add("123123@qq.com");
//        userNames.add("xx02@sina.com");
//        userNames.add("milddot@sina.com");
//        removeInalidEmail(userNames);
//        for(String userName : userNames){
//            System.out.println(userName);
//        }
        System.out.println(isAddressValid("service@medcn.cn"));
    }

}
