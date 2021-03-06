package com.xwintop.xJavaFxTool.controller.debugTools.redisTool;

import com.xwintop.xJavaFxTool.controller.IndexController;
import com.xwintop.xJavaFxTool.model.RedisToolDataTableBean;
import com.xwintop.xJavaFxTool.services.debugTools.redisTool.RedisToolDataTableService;
import com.xwintop.xJavaFxTool.view.debugTools.redisTool.RedisToolDataTableView;
import com.xwintop.xcore.util.RedisUtil;

import java.net.URL;
import java.util.ResourceBundle;

import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Lazy;

@Getter
@Setter
@Log4j
@Lazy
@FXMLController
public class RedisToolDataTableController extends RedisToolDataTableView {
	private RedisToolDataTableService redisToolDataTableService = new RedisToolDataTableService(this);
	private RedisToolController redisToolController;
	private String tabName;
	private RedisUtil redisUtil;
	private ObservableList<RedisToolDataTableBean> tableData = FXCollections.observableArrayList();

//	public static FXMLLoader getFXMLLoader() {
//		FXMLLoader fXMLLoader = new FXMLLoader(
//				IndexController.class.getResource("/fxml/debugTools/redisTool/RedisToolDataTable.fxml"));
//		return fXMLLoader;
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initView();
		initEvent();
		initService();
	}

	private void initView() {
		dataNameTableColumn.setCellValueFactory(new PropertyValueFactory<RedisToolDataTableBean, String>("name"));
		dataNameTableColumn.setCellFactory(TextFieldTableCell.<RedisToolDataTableBean>forTableColumn());
		dataNameTableColumn.setOnEditCommit((CellEditEvent<RedisToolDataTableBean, String> t) -> {
			t.getRowValue().setName(t.getNewValue());
		});
		dataTypeTableColumn.setCellValueFactory(new PropertyValueFactory<RedisToolDataTableBean, String>("type"));
		dataTypeTableColumn.setCellFactory(TextFieldTableCell.<RedisToolDataTableBean>forTableColumn());
		dataTypeTableColumn.setOnEditCommit((CellEditEvent<RedisToolDataTableBean, String> t) -> {
			t.getRowValue().setType(t.getNewValue());
		});
		dataSizeTableColumn.setCellValueFactory(new PropertyValueFactory<RedisToolDataTableBean, String>("size"));
		dataSizeTableColumn.setCellFactory(TextFieldTableCell.<RedisToolDataTableBean>forTableColumn());
		dataSizeTableColumn.setOnEditCommit((CellEditEvent<RedisToolDataTableBean, String> t) -> {
			t.getRowValue().setSize(t.getNewValue());
		});
		dataTimeTableColumn.setCellValueFactory(new PropertyValueFactory<RedisToolDataTableBean, String>("time"));
		dataTimeTableColumn.setCellFactory(TextFieldTableCell.<RedisToolDataTableBean>forTableColumn());
		dataTimeTableColumn.setOnEditCommit((CellEditEvent<RedisToolDataTableBean, String> t) -> {
			t.getRowValue().setTime(t.getNewValue());
		});
		dataTableView.setItems(tableData);
	}

	private void initEvent() {
		dataTableView.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				redisToolDataTableService.addTableViewContextMenu();
			} else if (event.getButton() == MouseButton.PRIMARY) {
				RedisToolDataTableBean redisToolDataTableBean = dataTableView.getSelectionModel().getSelectedItem();
				if (redisToolDataTableBean != null) {
//					System.out.println(redisToolDataTableBean.getName());
					log.info(redisToolDataTableBean.getName());
					redisToolDataTableService.addRedisToolDataViewTab(redisToolDataTableBean.getName());
				}
			}
		});
	}

	private void initService() {
	}

	public void setData(RedisToolController redisToolController, RedisUtil redisUtil) {
		this.redisToolController = redisToolController;
		this.redisUtil = redisUtil;
		redisToolDataTableService.reloadTableData();
	}
}