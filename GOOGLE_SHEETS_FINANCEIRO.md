# Relatório financeiro no Google Sheets

Crie uma aba `Financeiro` e publique apenas essa aba como CSV. Use exatamente estas colunas:

`Competência,Data,Tipo,Categoria,Descrição,Valor`

Exemplo: `2026-01,10/01/2026,ENTRADA,Contribuições,Arrecadação,500.00`

No servidor Tomcat, configure a variável de ambiente `GOOGLE_SHEETS_FINANCEIRO_URL` com a URL CSV publicada pela planilha. Ela não deve conter senha, chave de API ou credencial de conta. Para uma planilha privada, a próxima etapa é configurar uma conta de serviço Google fora do repositório.
