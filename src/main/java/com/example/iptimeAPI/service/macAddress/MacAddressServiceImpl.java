package com.example.iptimeAPI.service.macAddress;

import com.example.iptimeAPI.web.dto.MacAddressEditDTO;
import com.example.iptimeAPI.web.dto.MacAddressRegistDTO;
import com.example.iptimeAPI.domain.macAddress.MacAddress;
import com.example.iptimeAPI.domain.macAddress.MacAddressRepository;
import com.example.iptimeAPI.domain.macAddress.MacAddressService;
import com.example.iptimeAPI.web.exception.MacAddressValidateError;
import com.example.iptimeAPI.web.exception.MacAddressValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MacAddressServiceImpl implements MacAddressService {

    private final MacAddressRepository repository;

    @Override
    @Transactional
    public void registerMacAddress(MacAddressRegistDTO macAddressRegistDTO) {
        Optional<MacAddress> byMemberId = repository.findByMemberId(macAddressRegistDTO.getMemberId());
        Optional<MacAddress> byMacAddress = repository.findByMacAddress(macAddressRegistDTO.getMacAddress());

        if (byMacAddress.isPresent()) {
            throw new MacAddressValidateException(MacAddressValidateError.DUPLICATE_MACADDRESS);
        }
        if (byMemberId.isPresent()) {
            MacAddress macAddress = byMemberId.get();
            if (macAddress.isSameMacAddress(macAddressRegistDTO.getMacAddress())) {
                throw new MacAddressValidateException(MacAddressValidateError.DUPLICATE_MACADDRESS);
            }
            this.editMacAddress(new MacAddressEditDTO(macAddress.getId(), macAddressRegistDTO.getMemberId(), macAddressRegistDTO.getMacAddress()));
        }
        if (byMemberId.isEmpty()) {
            repository.save(new MacAddress(macAddressRegistDTO.getMemberId(), macAddressRegistDTO.getMacAddress()));
        }
    }

    @Override
    @Transactional
    public void editMacAddress(MacAddressEditDTO macAddressEditDTO) {
        repository.save(new MacAddress(macAddressEditDTO.getId(), macAddressEditDTO.getMemberId(), macAddressEditDTO.getMacAddress()));
    }

    @Override
    public List<MacAddress.MacAddressResponseDTO> browseMacAddresses() {
        return repository.findAll()
                .stream()
                .map(macAddress -> new MacAddress.MacAddressResponseDTO(macAddress.getId(), macAddress.getMemberId(), macAddress.getMacAddress()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> browseMacAddressesMembers() {
        List<MacAddress> all = repository.findAll();
        return all.stream()
                .map(macAddress -> macAddress.getMemberId())
                .collect(Collectors.toList());
    }

    @Override
    public MacAddress.MacAddressResponseDTO findMemberMacAddress(Long memberId) {
        Optional<MacAddress> byMemberId = repository.findByMemberId(memberId);
        if (byMemberId.isEmpty()) {
            throw new MacAddressValidateException(MacAddressValidateError.NOT_REGISTER_MEMBER);
        }
        MacAddress macAddress = byMemberId.get();
        return new MacAddress.MacAddressResponseDTO(macAddress.getId(), macAddress.getMemberId(), macAddress.getMacAddress());
    }
}
