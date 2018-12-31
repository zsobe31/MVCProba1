
package backend;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author zsobe31
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
            try {
            PrintWriter out = response.getWriter(); 
                                   
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("MVCProba1PU");
            EntityManager em = emf.createEntityManager();                        
            
            if(request.getParameter("task").equals("betolt")){
                List<Boritek> boritekok = Boritek.getAllBoritek(em);
                JSONArray boritekokJ = new JSONArray();
                for(Boritek b : boritekok){
                    JSONObject j = new JSONObject();
                    j.put("id", b.getId());
                    j.put("cimzet", b.getCimzet());
                    j.put("felado", b.getFelado());
                    j.put("meret", b.getMeret());
                    j.put("ido", b.getIdo());
                    j.put("suly", b.getSuly());
                    boritekokJ.put(j);
                }
                out.print(boritekokJ.toString()); 
            }
            
            if(request.getParameter("task").equals("login") && request.getParameter("captcha").equals("")){
                String user = request.getParameter("username");
                String passwd = request.getParameter("password");
                Felhasznalo f = Felhasznalo.login(em, user, passwd);
                if(f != null){
                    request.getSession().setAttribute("user", f);
                    // request.getSession().getAttribute("user"); -> ezt követően így érjük el a sessont
                    JSONObject j = new JSONObject();
                    j.put("result", "Üdvözlünk kedves " + f.getFelhasznalonev());
                    j.put("success", "1");
                    out.print(j.toString());
                }
                else{
                    JSONObject j = new JSONObject();
                    j.put("result", "Hibás felhasználónév vagy jelszó!");
                    j.put("success", "0");
                    out.print(j.toString());
                }
            }
            
            
            // if(request.getParameter....task equals userdata)
            if(request.getParameter("task").equals("userdata")){
                JSONObject vissza = new JSONObject();
                if(request.getSession().getAttribute("user") != null){
                    Felhasznalo f = (Felhasznalo)request.getSession().getAttribute("user");
                    vissza.put("success", "1");
                    vissza.put("nev", f.getFelhasznalonev());
                }
                else{
                    vissza.put("success", "0");
                }
                out.print(vissza.toString());
            }
            // megnézzük, hogy van-e user nevű session-ünk
            // request.getSession().getAttribute("user") != null
            // vissza: success: 1
            // vissza: success: 0
            
        }
        catch(Exception ex){
            System.out.println("Hiba: " + ex.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
