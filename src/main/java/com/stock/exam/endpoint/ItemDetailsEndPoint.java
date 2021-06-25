package com.stock.exam.endpoint;

import com.stock.exam.bean.Item;
import com.stock.exam.items.*;
import com.stock.exam.repository.IItemRepository;
import com.stock.exam.repository.ISupplierRepository;
import com.stock.exam.suppliers.*;
import com.stock.exam.bean.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.List;
import java.util.Optional;




@Endpoint
public class ItemDetailsEndPoint {


    @Autowired
    private IItemRepository itemRepository;

    @Autowired
    private ISupplierRepository supplierRepository;

    @PayloadRoot(namespace = "http://exam.stock.com/students",localPart = "GetItemDetails")
    @ResponsePayload
    public GetItemDetailsResponse findItem(@RequestPayload GetItemDetailsRequest request)
    {
        Item item= itemRepository.findById(request.getId()).get();
        GetItemDetailsResponse itemDetailsResponse = mapItemDetails(item);
        return itemDetailsResponse;
    }



    @PayloadRoot(namespace = "http://exam.stock.com/items",localPart = "GetAllItemDetailsRequest")
    @ResponsePayload

    public GetAllItemDetailsResponse getAllItems(@RequestPayload GetAllItemDetailsRequest request)
    {
        GetAllItemDetailsResponse itemResp = new GetAllItemDetailsResponse();
        System.out.println("Reached here");
        List<Item> items = itemRepository.findAll();
        System.out.println("List: "+ items);

        for(Item item: items){
            GetItemDetailsResponse studentDetailsResponse = mapItemDetails(item);
            itemResp.getItemDetails().add(studentDetailsResponse.getItemDetails());
        }

        return  itemResp;
    }



    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/items", localPart = "CreateItemDetailsRequest")
    @ResponsePayload
    public CreateItemDetailsResponse save(@RequestPayload CreateItemDetailsRequest request) {
        Supplier supplier = supplierRepository.findById(request.getItemDetails().getSupplierId()).get();

        Item testItem = itemRepository.save(new Item(
                request.getItemDetails().getId(),
                request.getItemDetails().getName(),
                request.getItemDetails().getItemCode(),
                request.getItemDetails().getStatus(),
                request.getItemDetails().getPrice(),
                supplier
        ));

        System.out.println("Test: "+testItem);

        CreateItemDetailsResponse studentDetailsResponse = new CreateItemDetailsResponse();
        studentDetailsResponse.setItemDetails(request.getItemDetails());
        studentDetailsResponse.setMessage("Created Successfully");
        return studentDetailsResponse;
    }


    @PayloadRoot(namespace = "http://schoolsoapapi.edu.com/items", localPart = "DeleteItem")
    @ResponsePayload
    public DeleteItemDetailsResponse delete(@RequestPayload DeleteItemDetailsRequest request) {

        System.out.println("ID: "+request.getId());
        itemRepository.deleteById(request.getId());
        DeleteItemDetailsResponse itemDetailsResponse = new DeleteItemDetailsResponse();
        itemDetailsResponse.setMessage("Deleted Successfully");
        return itemDetailsResponse;
    }



    private GetItemDetailsResponse mapItemDetails(Item item){
        ItemDetails details = mapItem(item);
        GetItemDetailsResponse itemDetailsResponse = new GetItemDetailsResponse();
        itemDetailsResponse.setItemDetails(details);
        return itemDetailsResponse;
    }

    private UpdateItemDetailsResponse mapUpdateStudentDetails(Item itm, String message) {
        ItemDetails details = mapItem(itm);
        UpdateItemDetailsResponse resp = new UpdateItemDetailsResponse();
        resp.setItemDetails(details);
        resp.setMessage(message);
        return resp;
    }

    private ItemDetails mapItem(Item item){
        ItemDetails itmDetails = new ItemDetails();
        itmDetails.setName(item.getName());
        itmDetails.setId(item.getId());
        itmDetails.setItemCode(item.getItemCode());
        itmDetails.setStatus(item.getStatus());
        itmDetails.setPrice(item.getPrice());
        itmDetails.setSupplierId(item.getSupplier().getId());
        return itmDetails;
    }
}
