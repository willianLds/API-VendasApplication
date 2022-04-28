package io.github.willianlds.service;

import io.github.willianlds.entity.Pedido;
import io.github.willianlds.enums.StatusPedido;
import io.github.willianlds.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService  {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> ObterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
