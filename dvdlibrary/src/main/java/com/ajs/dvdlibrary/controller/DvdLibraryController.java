package com.ajs.dvdlibrary.controller;

import java.util.List;
import java.util.Map;

import com.ajs.dvdlibrary.dao.DvdLibraryDao;
import com.ajs.dvdlibrary.dao.DvdLibraryDaoException;
import com.ajs.dvdlibrary.dto.Dvd;
import com.ajs.dvdlibrary.ui.DvdLibraryView;
import com.ajs.dvdlibrary.ui.UserIO;
import com.ajs.dvdlibrary.ui.UserIOConsoleImpl;



public class DvdLibraryController{

    private DvdLibraryView view;
    private DvdLibraryDao dao;
    private UserIO io = new UserIOConsoleImpl();
    
    
    public DvdLibraryController(DvdLibraryDao dao, DvdLibraryView view) {
        this.dao = dao;
        this.view = view;
    }

    
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        createDvd();
                        break;
                    case 2:
                    	removeDvd();
                        break;
                    case 3:
                        editDvd();
                        break;
                    case 4:
                    	listDvds();
                        break;
                    case 5:
                    	viewDvd();
                    	break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (DvdLibraryDaoException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

    private void createDvd() throws DvdLibraryDaoException {
        view.displayCreateDvdBanner();
        Dvd newDvd = view.getNewDvdInfo();
        dao.addDvd(newDvd.getId(), newDvd);
        view.displayCreateSuccessBanner();
    }

    private void listDvds() throws DvdLibraryDaoException {
        view.displayDisplayAllBanner();
        List<Dvd> dvdList = dao.getAllDvds();
        view.displayDvdList(dvdList);
    }

    private void viewDvd() throws DvdLibraryDaoException {
        view.displayDisplayDvdBanner();
        String dvdTitle = view.getDvdTitleChoice();
        Dvd dvd = dao.getDvd(dvdTitle);
        view.displayDvd(dvd);
    }

    private void removeDvd() throws DvdLibraryDaoException {
    	
    	String choice = "";
        view.displayRemoveDvdBanner();
        String dvdTitle = view.getDvdTitleChoice();
        List<Dvd> arr = dao.removeDvd(dvdTitle);
        if (arr.size()>1) {
        	
			for(Dvd currentDvd : arr) {
				String dvdInfo = String.format("DVD :ID = %s : %s : %s : %s : %s :%s \n",
						  currentDvd.getId(),
		                  currentDvd.getTitle(),
		                  currentDvd.getReleaseDate(),
		                  currentDvd.getMpaaRating(),
		                  currentDvd.getDirectorName(),
		                  currentDvd.getStudio(),
		                  currentDvd.getUserNote());
				System.out.print(dvdInfo);
				
			}
			
			choice = view.displayPickDvdBanner();
			
			
        }
        else {
        	
        	choice = arr.get(0).getId();
        }
        
        Dvd removedDvd = dao.removeThisDvd(choice);
        
        view.displayRemoveResult(removedDvd);
    }
    
    private void editDvd() throws DvdLibraryDaoException {
    	
    	String choice = "";
    	
    	view.displayEditDvdBanner();
        String dvdTitle = view.getDvdTitleChoice();
        List<Dvd> arr = dao.removeDvd(dvdTitle);
        if (arr.size()>1) {
        	
			for(Dvd currentDvd : arr) {
				String dvdInfo = String.format("DVD :ID = %s : %s : %s : %s : %s :%s \n",
						  currentDvd.getId(),
		                  currentDvd.getTitle(),
		                  currentDvd.getReleaseDate(),
		                  currentDvd.getMpaaRating(),
		                  currentDvd.getDirectorName(),
		                  currentDvd.getStudio(),
		                  currentDvd.getUserNote());
				System.out.print(dvdInfo);
				
			}
			
			choice = view.displayPickDvdBanner();
			
			
        }
        else {
        	
        	choice = arr.get(0).getId();
        }
        
        Dvd removedDvd = dao.removeThisDvd(choice);
    	
    	
    	
    	
    	
    	
    	
    	Dvd newDvd = view.getNewDvdInfo();
    	dao.addDvd(newDvd.getId(), newDvd);
    	view.displayEditSuccessBanner();
    	
    	//removeDvd();
    	//createDvd();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
