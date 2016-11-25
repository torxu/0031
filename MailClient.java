/**
 * A class to model a simple email client. The client is run by a
 * particular user, and sends and retrieves mail via a particular server.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MailClient
{
    // The server used for sending and receiving.
    private MailServer server;
    // The user running this client.
    private String user;
    private boolean activar;
    private String subject;
    private String message;
    private MailItem ultimoMensaje;


    /**
     * Create a mail client run by user and attached to the given server.
     */
    public MailClient(MailServer server, String user)
    {
        this.server = server;
        this.user = user;
        subject = "";
        message = "";
        activar = false;
        ultimoMensaje = null;
    }
    
    public void activarRespuesta()
    {
        activar = !activar;
    }

    /**
     * Return the next mail item (if any) for this user.
     */
    public MailItem getNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        ultimoMensaje = item;
        if(activar && item != null){
            MailItem email = new MailItem(user, item.getFrom(), subject, message);
            server.post(email);
        }
        if(item != null){
            ultimoMensaje = item;
        }
        return item;
    }

    /**
     * Print the next mail item (if any) for this user to the text 
     * terminal.
     */
    public void printNextMailItem()
    {
        MailItem item = server.getNextMailItem(user);
        if(item == null) {
            System.out.println("No new mail.");
        }
        else {
            item.print();
        }
        if(activar && item != null){
            MailItem email = new MailItem(user, item.getFrom(), subject, message);
            server.post(email);
        }
        if(item != null){
            ultimoMensaje = item;
        }
    }
    
    public void fijarRespuesta(String asunto, String mensaje)
    {
        this.subject = asunto;
        this.message = mensaje;
    }
    

    /**
     * Send the given message to the given recipient via
     * the attached mail server.
     * @param to The intended recipient.
     * @param message The text of the message to be sent.
     */
    public void sendMailItem(String to, String message, String subject)
    {
        MailItem item = new MailItem(user, to, message, subject);
        server.post(item);
    }
    
    public void amountMailItem()
    {
        int amount;
        amount = server.howManyMailItems(user);
        System.out.println("Numero de mails: " + amount);
    }
    
    public void imprimirUltimoMensaje()
    {
        if(ultimoMensaje == null){
            System.out.println("No tiene mensajes que leer");
        }
        else{
            ultimoMensaje.print();
        }
    }
}
