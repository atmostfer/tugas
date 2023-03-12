package id.kawahedukasi.controller;

import com.opencsv.exceptions.CsvValidationException;
import id.kawahedukasi.dto.FileFormDTO;
import id.kawahedukasi.model.Store;
import id.kawahedukasi.service.ExportService;
import id.kawahedukasi.service.ImportService;
import id.kawahedukasi.service.StoreService;
import net.sf.jasperreports.engine.JRException;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;

@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class StoreController {

    @Inject
    StoreService storeService;

    @Inject
    ExportService exportService;

    @Inject
    ImportService importService;

    @GET
    public Response inputPathParameter() {
        return storeService.get();
    }
    @GET
    @Path("/export/pdf")
    @Produces("application/pdf")
    public Response exportPdf() throws JRException {
        return exportService.exportPdfStore();
    }
    @GET
    @Path("/export/excel")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response exportExcel() throws IOException {
        return exportService.exportExcelStore();
    }
    @GET
    @Path("/export/csv")
    @Produces("text/csv")
    public Response exportCsv() throws IOException {
        return exportService.exportCsvStore();
    }
    @GET
    @Path("/{Id}")
    public Response inputPathParameter(@PathParam("Id") Long id) {
        Store store = Store.findById(id);
        if (store == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(Store.find("id = ?1", id).list()).build();
    }

    @POST
    @Transactional
    public Response post(Map<String, Object> request) {
        return storeService.post(request);
    }

    @POST
    @Path("/import/csv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importCSV(@MultipartForm FileFormDTO request) {
        try {
            return importService.importCSV(request);
        } catch (IOException | CsvValidationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response put(@PathParam("id") Long id, Map<String, Object> request) {
        return storeService.put(id, request);
    }

    @POST
    @Path("/import/excel")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importExcel(@MultipartForm FileFormDTO request) throws IOException {
        return importService.importExcel(request);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id, Map<String, Object> request) {
        return storeService.delete(id);
    }
}
