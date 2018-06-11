import java.io.*;
import java.net.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Pop3Client {

  public static void main(String args[]) throws Exception {

    // mail server connection parameters
    String host; 
    String user;      
    String password;  
    String liner = "---------------------------------";
    Scanner scanner = new Scanner(System.in);

    System.out.println(liner + "\nPOP3 Mail Client Application.\n" +liner);
    System.out.println("\nPlease enter the Host address : ");

    host = scanner.nextLine();

    System.out.println("\nPlease enter username : ");

    user = scanner.nextLine();

    System.out.println("\nPlease enter password : ");

    password = scanner.nextLine();

    System.out.println("\n" + liner +"\n" +
      "Connecting to mail server.....\n" + liner + "\n");


    // connect to my pop3 inbox
    Properties properties = System.getProperties();
    Session session = Session.getDefaultInstance(properties);
    Store store = session.getStore("pop3");
    store.connect(host, user, password);
    Folder inbox = store.getFolder("Inbox");
    inbox.open(Folder.READ_ONLY);

    // get the list of inbox messages
    Message[] messages = inbox.getMessages();

    if (messages.length == 0) System.out.println("No messages found.");

    for (int i = 0; i < messages.length; i++) {
      // stop after listing ten messages
      if (i > 10) {
        System.exit(0);
        inbox.close(true);
        store.close();
      }

      System.out.println("Message " + (i + 1));
      System.out.println("From : " + messages[i].getFrom()[0]);
      System.out.println("Subject : " + messages[i].getSubject());
      System.out.println("Sent Date : " + messages[i].getSentDate());
      System.out.println();
    }

    inbox.close(true);
    store.close();
  }
}