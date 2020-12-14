package subway.domain.menu.submenu.action;

import subway.domain.LineRepository;
import subway.domain.StationRepository;
import subway.domain.menu.constant.CategoryType;
import subway.domain.menu.constant.CommonMessage;
import subway.domain.menu.submenu.action.constant.ActionMessage;
import subway.view.InputView;

public class DeleteAction extends Action {
    public DeleteAction(char order, String category, String actionType, InputView inputView) {
        super(order, category, actionType, inputView);
    }

    @Override
    public void runAction() {
        if (category.equals(CategoryType.SECTION)) {
            deleteSection();
            return;
        }

        printDeleteMessage();
        String name = inputDelete();
        if (category.equals(CategoryType.STATION)) {
            deleteStation(name);
        }

        if (category.equals(CategoryType.LINE)) {
            deleteLine(name);
        }
        printSuccessMessage();
    }

    private void printDeleteMessage() {
        System.out.println(CommonMessage.SHARP + CommonMessage.SHARP + CommonMessage.SPACE + ActionMessage.INPUT_DELETE
                + CommonMessage.SPACE + category + CommonMessage.SPACE + ActionMessage.INPUT_DELETE_NAME);
    }

    private void printDeleteMessage(String type) {
        System.out.print(CommonMessage.SHARP + CommonMessage.SHARP + CommonMessage.SPACE + ActionMessage.INPUT_DELETE
                + CommonMessage.SPACE + category + ActionMessage.INPUT_POSTPOSITION + CommonMessage.SPACE);

        if (type.equals(CategoryType.LINE)) {
            System.out.print(CategoryType.LINE + ActionMessage.DELETE_SECTION_POSTPOSITION + CommonMessage.SPACE);
        }

        if (type.equals(CategoryType.STATION)) {
            System.out.print(CategoryType.STATION + ActionMessage.DELETE_SECTION_POSTPOSITION + CommonMessage.SPACE);
        }

        System.out.println(ActionMessage.INPUT_SECTION_MESSAGE);
    }

    private String inputDelete() {
        String name = inputView.getScanner().nextLine();
        System.out.println();
        return name;
    }

    private void deleteStation(String name) {
        StationRepository.deleteStation(name);
    }

    private void deleteLine(String name) {
        LineRepository.deleteLineByName(name);
    }

    private void deleteSection() {
        printDeleteMessage(CategoryType.LINE);
        String line = inputDelete();

        printDeleteMessage(CategoryType.STATION);
        String name = inputDelete();

        LineRepository.lines().stream().filter(item -> item.getName().equals(line)).findFirst().get()
                .deleteStation(name);

        printSuccessMessage();
    }

    private void printSuccessMessage() {
        System.out.println(CommonMessage.INFO + CommonMessage.SPACE + ActionMessage.SUCCESS_SUBWAY + CommonMessage.SPACE
                + category + ActionMessage.SUCCESS_POSTPOSITION + CommonMessage.SPACE + ActionMessage.SUCCESS_DELETE);
        System.out.println();
    }
}
