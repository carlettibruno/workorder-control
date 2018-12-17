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
		String[] contents = linha.split("\t");
		if(contents.length != 10) {
			throw new Exception("Padrão txt invalido.");
		}
		Cliente cliente = new Cliente();
		int i = 0;
		cliente.setCode(contents[i++].trim());
		cliente.setCpfCnpj(contents[i++].trim());
		cliente.setNome(contents[i++].trim());
		cliente.setEmail(contents[i++].trim());
		Endereco endereco = new Endereco();
		endereco.setEndereco(contents[i++].trim());
		endereco.setCep(contents[i++].trim());
		endereco.setComplemento(contents[i++].trim());
		endereco.setBairro(contents[i++].trim());
		endereco.setEstado(contents[i++].trim());
		endereco.setCidade(contents[i++].trim());
		cliente.setEndereco(endereco);
		
		return cliente;
	}
	
}