package lombok;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Parent {
    private String foo;

    @Getter(AccessLevel.NONE)
    private Boolean isBar;

    public boolean isBar() {
        return isBar == null ? false : isBar;
    }
}
