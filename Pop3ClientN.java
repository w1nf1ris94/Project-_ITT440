import java.io.*;
import java.net.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Pop3ClientN {

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

    System.out.println("\n" + liner +"\n" + "Connecting to mail server.....\n");

    // connect to my pop3 inbox
    Properties properties = System.getProperties();
    Session session = Session.getDefaultInstance(properties);
    Store store = session.getStore("pop3");
    store.connect(host, user, password);

    System.out.println("Connected!\n" + liner + "\n");

    Scanner input = new Scanner(System.in);
    boolean mainLoop = true;
    int choice;

    while(true){

      System.out.println("Mail Option:\n");
      System.out.print("1.) Read Mail \n");
      System.out.print("2.) Delete Mail \n");
      System.out.print("3.) Exit\n");
      System.out.print("\nEnter Your Menu Choice: ");

      choice = input.nextInt();

      switch(choice){

        case 1:

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
          //store.close();
                    
          break;

        case 2: 

          Folder emailFolder = store.getFolder("INBOX");
          emailFolder.open(Folder.READ_WRITE);

          BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
          // retrieve the messages from the folder in an array and print it
          Message[] messagesN = emailFolder.getMessages();
          System.out.println("messages.length---" + messagesN.length);

          for (int i = 0; i < messagesN.length; i++) {

            Message message = messagesN[i];
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (i + 1));
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);

            String subject = message.getSubject();
            System.out.print("Do you want to delete this message [y/n] ? ");
            String ans = reader.readLine();

            if ("Y".equals(ans) || "y".equals(ans)) {
              // set the DELETE flag to true
              message.setFlag(Flags.Flag.DELETED, true);
              System.out.println("Marked DELETE for message: " + subject);
            } else if ("n".equals(ans)) {
              break;
            }
          }
          // expunges the folder to remove messages which are marked deleted
          emailFolder.close(true);
          //store.close();
          break;

        case 3:
          store.close();
          System.out.println("Exiting Program...");
          System.exit(0);
          break;

        default :
          System.out.println("This is not a valid Menu Option! Please Select Another");
          break;
      }
    }
  }
}