package org.csi.controle.servico.util;

import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.csi.controle.core.entidade.Cliente;
import org.csi.controle.core.entidade.Endereco;
import org.csi.controle.core.entidade.NotaFiscal;

public class ConversorTxt {

	public List<Endereco> planilhaToEndereco(InputStream inputStream) throws Exception {
		List<Endereco> enderecos = new ArrayList<Endereco>();
		
        Workbook wb = WorkbookFactory.create(inputStream);
        Sheet sheet = wb.getSheetAt(0);
        int rowsCount = sheet.getLastRowNum();
        for (int i = 1; i <= rowsCount; i++) {
            Row row = sheet.getRow(i);
            int j = 0;
            Endereco endereco = new Endereco();
            enderecos.add(endereco);
            endereco.setTitulo(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
            j++;
            endereco.setEndereco(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
            j++;
            endereco.setCep(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
            j++;
            endereco.setComplemento(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
            j++;
            endereco.setBairro(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
            j++;
            endereco.setCidade(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
            j++;
            endereco.setEstado(row.getCell(j) != null ? row.getCell(j).getStringCellValue() : "");
        }
        
		return enderecos;
	}
	
	public NotaFiscal txtToNotaFiscal(String linha) throws Exception {
		if(linha.length() != 83) {
			throw new Exception("Padrão txt invalido.");
		}
		Date dataVencimento = new SimpleDateFormat("dd/MM/yyyy").parse(linha.substring(64, 75).trim());
		
		NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("pt","BR"));
		formatter.setMinimumFractionDigits(4);
		Double valor = null;
		try {
			valor = formatter.parse(linha.substring(50, 64).trim()).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}		
		
		String nroNotaStr = linha.substring(41, 50).split("\\.")[1].trim();
		Long nroNota = null;
		if(!nroNotaStr.trim().isEmpty()) {
			nroNota = new Long(nroNotaStr);
		}
		Boolean paga = linha.substring(75, 83).trim().equalsIgnoreCase("SIM");
		NotaFiscal notaFiscal = new NotaFiscal(valor, dataVencimento, nroNota, paga);
		return notaFiscal;
	}
	
	public List<String> txtToIdOrdemServicoNotaFiscal(String linha) throws Exception {
		if(linha.length() != 83) {
			throw new Exception("Padrão txt invalido.");
		}
		List<String> ids = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(linha.substring(0, 41).trim(), ";");
		while(st.hasMoreTokens()) {
			String idCompleto = st.nextToken();
			ids.add(idCompleto.substring(0, 6));
		}
		return ids;
	}
	
	public Cliente txtToCliente(String linha) throws Exception {
		if(linha.length() != 303 && linha.length() != 304) {
			throw new Exception("Padrão txt invalido.");
		}
		Cliente cliente = new Cliente();
		cliente.setCpfCnpj(linha.substring(0, 19).trim());
		cliente.setNome(linha.substring(19, 45).trim());
		cliente.setEmail(linha.substring(45, 96).trim());
		Endereco endereco = new Endereco();
		endereco.setEndereco(linha.substring(96, 152).trim());
		endereco.setCep(linha.substring(152, 162).trim());
		endereco.setComplemento(linha.substring(162, 243).trim());
		endereco.setBairro(linha.substring(243, 269).trim());
		endereco.setEstado(linha.substring(269, 273).trim());
		endereco.setCidade(linha.substring(273, 303).trim());
		cliente.setEndereco(endereco);
		
		return cliente;
	}
	
}