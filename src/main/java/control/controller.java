/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import net.daw.bean.Fila;
import net.daw.bean.Celda;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import static java.lang.System.out;
import net.daw.bean.Errores;

/**
 *
 * @author Alejandro
 */
public class controller extends HttpServlet {

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

        response.setContentType("application/jSon");

        //response.setStatus(200);
        PrintWriter out = response.getWriter();
        Integer ancho = null;
        Integer alto = null;
        Gson gSon = new Gson();
        try {
            ancho = Integer.parseInt(request.getParameter("ancho"));
            alto = Integer.parseInt(request.getParameter("alto"));
        } catch (NumberFormatException nfex) {
            response.setStatus(500);
            Errores error = new Errores();
            error.setMensaje("Por favor, ingrese dos numeros enteros en el formulario");
            error.setAlto(request.getParameter("alto"));
            error.setAncho(request.getParameter("ancho"));
            response.getWriter().append(gSon.toJson(error));

        }

        if (!(ancho > 0 && ancho <= 100) || !(alto > 0 && alto <= 100)) {
            response.setStatus(500);
            Errores error = new Errores();
            error.setMensaje("El numero debe estar entre 1 y 100");
            error.setAlto(request.getParameter("alto"));
            error.setAncho(request.getParameter("ancho"));
            response.getWriter().append(gSon.toJson(error));
        } else {
            try {

                ArrayList<Fila> arrayFS = new ArrayList<Fila>();

                for (int i = 1; i <= alto; i++) {

                    Fila miFila = new Fila();
                    miFila.setI(i);
                    arrayFS.add(miFila);
                    ArrayList<Celda> arrayC = new ArrayList<Celda>();
                    miFila.setArray(arrayC);

                    for (int j = 1; j <= ancho; j++) {

                        Celda miCel = new Celda();
                        miCel.setI(i);
                        miCel.setJ(j);
                        miCel.setResultado(i * j);
                        miFila.getArray().add(miCel);

                    }

                }

                response.getWriter().append(gSon.toJson(arrayFS));

            } finally {
                out.close();
            }
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
