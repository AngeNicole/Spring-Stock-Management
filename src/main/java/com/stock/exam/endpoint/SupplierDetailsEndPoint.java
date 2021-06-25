package com.stock.exam.endpoint;

import com.stock.exam.bean.Supplier;
import com.stock.exam.repository.IItemRepository;
import com.stock.exam.repository.ISupplierRepository;
import com.stock.exam.suppliers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.Optional;


@Endpoint
public class SupplierDetailsEndPoint {


    @Autowired
    private ISupplierRepository supplierRepository;

    @PayloadRoot(namespace = "http://exam.stock.com/suppliers", localPart = "GetSupplierDetailsRequest")
    @ResponsePayload
    public GetSupplierDetailsResponse findById(@RequestPayload GetSupplierDetailsRequest request) {

        Supplier supplier = supplierRepository.findById(request.getId()).get();

        GetSupplierDetailsResponse supplierDetailsResponse = mapSupplierDetails(supplier);
        return  supplierDetailsResponse;
    }

    @PayloadRoot(namespace = "http://exam.stock.com/suppliers", localPart = "GetAllSuppplierDetailsRequest")
    @ResponsePayload
    public GetAllSupplierDetailsResponse findAll(@RequestPayload GetAllSupplierDetailsRequest request){
        GetAllSupplierDetailsResponse allCourseDetailsResponse = new GetAllSupplierDetailsResponse();

        List<Supplier> suppliers = supplierRepository.findAll();
        for (Supplier supplier: suppliers){
            GetSupplierDetailsResponse courseDetailsResponse = mapSupplierDetails(supplier);
            allCourseDetailsResponse.getSupplierDetails().add(courseDetailsResponse.getSupplierDetails());
        }
        return allCourseDetailsResponse;
    }


    @PayloadRoot(namespace = "http://exam.stock.com/courses", localPart = "CreateSupplierDetailsRequest")
    @ResponsePayload
    public CreateSupplierDetailsResponse save(@RequestPayload CreateSupplierDetailsRequest request) {
        supplierRepository.save(new Supplier(request.getSupplierDetails().getId(),
                request.getSupplierDetails().getName(),
                request.getSupplierDetails().getEmail(),
                request.getSupplierDetails().getMobile()
        ));

        CreateSupplierDetailsResponse courseDetailsResponse = new CreateSupplierDetailsResponse();
        courseDetailsResponse.setSupplierDetails(request.getSupplierDetails());
        courseDetailsResponse.setMessage("Created Successfully");
        return courseDetailsResponse;
    }

    @PayloadRoot(namespace = "http://exam.stock.com/courses", localPart = "UpdateSupplierDetailsRequest")
    @ResponsePayload
    public UpdateSupplierDetailsResponse update(@RequestPayload UpdateSupplierDetailsRequest request) {
        UpdateSupplierDetailsResponse supplierDetailsResponse = null;
        Optional<Supplier> existingSupplier = this.supplierRepository.findById(request.getSupplierDetails().getId());
        if(existingSupplier.isEmpty() || existingSupplier == null) {
            supplierDetailsResponse = mapSupplierDetail(null, "Id not found");
        }
        if(existingSupplier.isPresent()) {

            Supplier _supplier = existingSupplier.get();
            _supplier.setName(request.getSupplierDetails().getName());
            _supplier.setEmail(request.getSupplierDetails().getEmail());
            _supplier.setMobile(request.getSupplierDetails().getMobile());
            supplierRepository.save(_supplier);
            supplierDetailsResponse = mapSupplierDetail(_supplier, "Updated successfully");

        }
        return supplierDetailsResponse;
    }

    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteSupplierDetailsResponse delete(@RequestPayload DeleteSupplierDetailsRequest request) {

        System.out.println("ID: "+request.getId());
        supplierRepository.deleteById(request.getId());

        DeleteSupplierDetailsResponse courseDetailsResponse = new DeleteSupplierDetailsResponse();
        courseDetailsResponse.setMessage("Deleted Successfully");
        return courseDetailsResponse;
    }

    private GetSupplierDetailsResponse mapSupplierDetails(Supplier supplier){
        SupplierDetails courseDetails = mapSupplier(supplier);

        GetSupplierDetailsResponse courseDetailsResponse = new GetSupplierDetailsResponse();

        courseDetailsResponse.setSupplierDetails(courseDetails);
        return courseDetailsResponse;
    }

    private UpdateSupplierDetailsResponse mapSupplierDetail(Supplier supplier, String message) {
        SupplierDetails courseDetails = mapSupplier(supplier);
        UpdateSupplierDetailsResponse courseDetailsResponse = new UpdateSupplierDetailsResponse();

        courseDetailsResponse.setSupplierDetails(courseDetails);
        courseDetailsResponse.setMessage(message);
        return courseDetailsResponse;
    }

    private SupplierDetails mapSupplier(Supplier course){
        SupplierDetails supplierDetails = new SupplierDetails();
        supplierDetails.setName(supplierDetails.getName());
        supplierDetails.setId(supplierDetails.getId());
        supplierDetails.setEmail(supplierDetails.getEmail());
        supplierDetails.setMobile(supplierDetails.getMobile());
        return supplierDetails;
    }
}
