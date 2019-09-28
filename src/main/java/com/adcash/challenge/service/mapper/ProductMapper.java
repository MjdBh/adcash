package com.adcash.challenge.service.mapper;

import com.adcash.challenge.domain.Product;
import com.adcash.challenge.service.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper( ProductMapper.class );

    ProductDTO mapProductToProductDTO(Product product);

    Product mapProductDTOToProduct(ProductDTO productDTO);

    default List<ProductDTO> mapProductToProductDTO(List<Product> products){
        if (products==null || products.isEmpty())
            return null;

        List<ProductDTO> result= new ArrayList<>();
        for(Product product:products)
            result.add(mapProductToProductDTO(product));
        return result;
    }

}
